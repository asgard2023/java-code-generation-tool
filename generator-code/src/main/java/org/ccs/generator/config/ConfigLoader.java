package org.ccs.generator.config;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class ConfigLoader {
    static ClassPathResource resource = new ClassPathResource("generatorConfig.xml");
    static DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
    static XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);

    static {
        reader.loadBeanDefinitions(resource);
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
            e.printStackTrace();
        }
        return null;
    }
}
