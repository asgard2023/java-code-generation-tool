package org.ccs.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * springboot+easyui/jqgrid代码生成工具
 *
 * @author chenjh
 */
@SpringBootApplication
@EnableConfigurationProperties
@EnableScheduling
@EnableAsync
public class GeneratorApplicatioin {
    public static final Logger logger = LoggerFactory.getLogger(GeneratorApplicatioin.class);

    public static void main(String[] args) {
        SpringApplication.run(GeneratorApplicatioin.class, args);
    }

}
