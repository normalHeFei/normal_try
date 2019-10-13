package com.normal.trysth.spring.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Annotation;
import java.util.stream.Stream;

/**
 * @author hefei
 * @date 2018/6/25
 */
public  class ZooBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    private static final Logger logger = LoggerFactory.getLogger(ZooBeanDefinitionRegistryPostProcessor.class);

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        String[] beanNames = registry.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            BeanDefinition beanDefinition = registry.getBeanDefinition(beanName);
            //wrap if is a config bean
            if (isConfigBean(beanDefinition)) {
                BeanDefinition zooConfigBean = wrapper(beanDefinition);
                //remove old definition
                registry.removeBeanDefinition(beanName);
                //add new definition
                registry.registerBeanDefinition(beanName, zooConfigBean);
            }
        }
    }

    protected BeanDefinition wrapper(BeanDefinition beanDefinition) {
        
        return beanDefinition;
    }

    public boolean isConfigBean(BeanDefinition beanDefinition) {
        String beanClassName = beanDefinition.getBeanClassName();
        try {
            Class<?> beanClazz = Class.forName(beanClassName);
            Annotation[] annotations = beanClazz.getAnnotations();
            if (annotations.length == 0) {
                return false;
            }
            boolean containConfigAnn = Stream.of(annotations)
                    .anyMatch(annotation -> annotation.annotationType().isAssignableFrom(Configuration.class));
            return containConfigAnn;
        } catch (ClassNotFoundException e) {
            logger.error("bean class not found", e);
        }
        return false;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
