package org.ccs.generator.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.extern.slf4j.Slf4j;
import org.ccs.generator.bean.Config;
import org.ccs.generator.config.ConfigLoader;
import org.ccs.generator.util.CodeGeneratorUtil;
import org.ccs.generator.util.FileZipUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        String type = request.getParameter("type");
        Config configDefault = (Config) ConfigLoader.getBean("config");
        log.info("----loadConfig generatorConfig.xml ok");
        if ("default".equals(type)) {
            config = configDefault;
        } else {
            Config tmp = new Config();
            //如果为空自动取默认
            BeanUtil.copyProperties(configDefault, tmp,
                    CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            BeanUtil.copyProperties(config, tmp,
                    CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            config = tmp;
        }
        log.info("---type={} tableName={} author={}", type, config.getTableName(), config.getAuthor());
        String path = CodeGeneratorUtil.generateCode(config);
        log.info("----generate code ok");
        FileZipUtils.createZipFile(path);
        log.info("----zip code file ok");
        FileZipUtils.zipFilesDowload(path, response);
        log.info("----download zipFile ok");
        return path;
    }

    @RequestMapping(value = "/defaultConfig", method = {RequestMethod.POST, RequestMethod.GET})
    public Object getDefaultConfig(HttpServletRequest request) {
        return ConfigLoader.getBean("config");
    }

}