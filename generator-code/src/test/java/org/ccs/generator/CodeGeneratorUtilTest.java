package org.ccs.generator;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.ccs.generator.bean.Config;
import org.ccs.generator.config.ConfigLoader;
import org.ccs.generator.util.CodeGeneratorUtil;
import org.junit.jupiter.api.Test;

public class CodeGeneratorUtilTest {
    @Test
    public void generateCode() {
        Config config = (Config) ConfigLoader.getBean("config");
        try {
            String path = CodeGeneratorUtil.generateCode(config);
            System.out.println(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void copyConfig() {
        Config config=new Config();
        config.setTableName("test1");

        Config config2=new Config();
//        config2.setTableName("test2");

        Config tmp=new Config();
        System.out.println(tmp.getTableName());
        BeanUtil.copyProperties(config, tmp,
                CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        System.out.println(tmp.getTableName());
//
        BeanUtil.copyProperties(config2, tmp,
                CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        System.out.println(tmp.getTableName());
    }
}
