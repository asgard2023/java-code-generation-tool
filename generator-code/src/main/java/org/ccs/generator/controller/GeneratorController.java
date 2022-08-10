package org.ccs.generator.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.ccs.generator.bean.Config;
import org.ccs.generator.config.ConfigLoader;
import org.ccs.generator.exceptions.FailedException;
import org.ccs.generator.util.CodeGeneratorUtil;
import org.ccs.generator.util.FileZipUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 代码生成
 *
 * @Version V1.0
 * @Title: DflRolecontroller
 * @Description: 角色表 Controller
 * @Author: Created by chenjh
 * @Date: 2022-5-3 20:25:42
 */
@RestController
@RequestMapping("/generator")
@Slf4j
public class GeneratorController {
    private static Map<String, Long> requestLockMap = new ConcurrentHashMap<>();

    /**
     * 生成代码
     *
     * @param request
     * @param config
     * @author chenjh
     * @date 2022-5-3 20:25:42
     */
    @RequestMapping(value = "/generate", method = {RequestMethod.POST, RequestMethod.GET})
    public Object generate(Config config, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long curTime = System.currentTimeMillis();
        String ip = ServletUtil.getClientIP(request);
        Long existTime = requestLockMap.get(ip);
        String type = request.getParameter("type");
        String swagger = request.getParameter("swagger");
        String[] uiTypeArrays = request.getParameterValues("uiTypes");
        config.setUiTypes(uiTypeArrays);
        config.setSwagger(false);
        if (StrUtil.equals("on", swagger)) {
            config.setSwagger(true);
        }
        if (existTime == null || curTime - existTime > 5 * 60000) {
            requestLockMap.put(ip, curTime);
            String path = null;
            try {
                path = generateCodeAndDownload(config, type, ip, response);
            } catch (Exception e) {
                log.error("-----generate--type={} ip={}", type, ip, e);
            }
            requestLockMap.remove(ip);
            return path;
        } else {
            throw new FailedException("您当前有代码生成正在处理，请稍后再试");
        }
    }

    /**
     * 代码生成并下载
     *
     * @param config   配置参数
     * @param type     生成类型
     * @param ip       访问IP
     * @param response 响应对象
     * @return 代码生成位置
     * @throws Exception
     */
    private String generateCodeAndDownload(Config config, String type, String ip, HttpServletResponse response) throws Exception {
        Config configDefault = (Config) ConfigLoader.getBean("config");
        log.info("----ip={} loadConfig generatorConfig.xml ok", ip);
        if ("default".equals(type)) {
            config = configDefault;
        } else {
            Config tmp = new Config();
            //如果为空自动取默认
            BeanUtil.copyProperties(configDefault, tmp,
                    CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            BeanUtil.copyProperties(config, tmp,
                    CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            tmp.setSwagger(config.isSwagger());
            tmp.setUiTypes(config.getUiTypes());
            config = tmp;
        }
        log.info("---ip={} type={} tableName={} author={} swagger={}", ip, type, config.getTableName(), config.getAuthor(), config.isSwagger());
        String path = CodeGeneratorUtil.generateCode(config);
        log.info("----ip={} generate code ok", ip);
        FileZipUtils.createZipFile(path);
        log.info("----ip={} zip code file ok", ip);
        FileZipUtils.zipFilesDowload(path, response);
        log.info("----ip={} download zipFile ok", ip);
        return path;
    }

    @RequestMapping(value = "/defaultConfig", method = {RequestMethod.POST, RequestMethod.GET})
    public Object getDefaultConfig(HttpServletRequest request) {
        return ConfigLoader.getBean("config");
    }

}