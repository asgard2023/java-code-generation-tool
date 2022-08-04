package org.ccs.opendfl.mysql.config;


import cn.hutool.extra.servlet.ServletUtil;
import org.ccs.opendfl.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


/**
 * 捕获异常统一处理
 *
 * @version v1.0
 * @modified by chenjh
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private Map<String, Object> getRequestMap(HttpServletRequest req) {
        Map<String, Object> reqMap = new TreeMap<>();
        if (req == null) {
            return reqMap;
        }

        //获得request 相关信息
        String method = req.getMethod();
        reqMap.put("method", method);

        String noLogStr = "token,";
        Set<String> keys = req.getParameterMap().keySet();
        for (String key : keys) {
            if (noLogStr.contains(key + ",")) {
                continue;
            }
            reqMap.put(key, req.getParameter(key));
        }
        reqMap.remove("password");
        reqMap.put("remoteAddr", ServletUtil.getClientIP(req));
        String requestURI = req.getRequestURI();
        reqMap.put("requestURI", requestURI);
        return reqMap;
    }


    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Object handleException(HttpServletRequest request, Exception ex) {
        if (ex == null) {
            logger.error("---method={} uri={} ex is null", request.getMethod(), request.getRequestURI());
            return null;
        }
        if (ex instanceof HttpRequestMethodNotSupportedException
                || ex instanceof HttpMediaTypeNotSupportedException
                || ex instanceof HttpMediaTypeNotAcceptableException) {
            logger.error("---handleException method={} uri={} error={}", request.getMethod(), request.getRequestURI(), ex.getMessage(), ex);
            ResultData resultData = ResultData.error(new FailedException());
            resultData.setErrorMsg(ex.getMessage());
            return resultData;
        }


        String messageError = ex != null ? ex.getMessage() : null;
        logger.error("---handleException method={} error={} \n request={}", request.getMethod(), messageError, this.getRequestMap(request), ex);

        ResultData resultData = ResultData.error(new UnknownException());
        resultData.setErrorType("sys");
        return resultData;
    }

    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResultData handleBaseException(HttpServletRequest request, BaseException e) {
        String logType = getLogExceptionTypeBase();
        Map<String, Object> parameterMap = this.getRequestMap(request);
        if (("full".equals(logType)) && !(e instanceof BaseException)) {
            logger.warn("----handleBaseException method={} request={}\n error:{}", request.getMethod(), parameterMap, e.getMessage(), e);
        } else {
            logger.warn("----handleBaseException method={} request={}\n error:{}", request.getMethod(), parameterMap, e.getMessage());
        }
        return ResultData.error(e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResultData validMethodArgumentError(HttpServletRequest request, MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        logger.error("---uri={} field={} rejected={} error={}", request.getRequestURI(), fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage());
        return ResultData.error(ResultCode.PARAMS_VALID_ERROR.getCode(), fieldError.getField() + fieldError.getDefaultMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResultData validError(HttpServletRequest request, BindException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        logger.error("---uri={} field={} rejected={} error={}", request.getRequestURI(), fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage());
        return ResultData.error(ResultCode.PARAMS_VALID_ERROR.getCode(), fieldError.getField() + fieldError.getDefaultMessage());
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ResultData> error(SecurityException e) {
        return new ResponseEntity<>(ResultData.error(e.getMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }


    private static String logExceptionTypeBase = "simple";
    private static Long baseExceptionTypeTime = 1000L;
    private static final int CACHE_TIME = 120000;

    private static String getLogExceptionTypeBase() {
        return logExceptionTypeBase;
    }

}