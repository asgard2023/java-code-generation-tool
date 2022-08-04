package org.ccs.opendfl.mysql;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author chenjh
 */
@SpringBootApplication
@EnableConfigurationProperties
@EnableSwagger2
@EnableKnife4j
@MapperScan(basePackages = "org.ccs.opendfl.mysql")
public class MysqlApplication {
    public static final Logger logger = LoggerFactory.getLogger(MysqlApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MysqlApplication.class, args);
    }
}
