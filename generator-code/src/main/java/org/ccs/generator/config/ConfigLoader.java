package org.ccs.generator.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Slf4j
public class ConfigLoader {
    private ConfigLoader(){

    }
    public static final String CONFIG_FILE_NAME = "generatorConfig.xml";
    static ClassPathResource resource = new ClassPathResource(CONFIG_FILE_NAME);
    static DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
    static XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);

    static {
        reader.loadBeanDefinitions(CONFIG_FILE_NAME);
    }

    public static Object getBean(String beanId) {
        return factory.getBean(beanId);
    }

    public static String getClassPath() {
        try {
            String path = resource.getFile().toString();
            path = path.substring(0, path.indexOf("target") + "target".length());
            return path;
        } catch (IOException e) {
            log.warn("-----getClassPath--fail={}", e.getMessage());
        }
        return "/tmp/";
    }
}
