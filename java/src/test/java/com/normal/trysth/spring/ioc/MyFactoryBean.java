package com.normal.trysth.spring.ioc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @author hefei
 * @date 2017/9/20
 */
public class MyFactoryBean implements FactoryBean<ComplexObj>, ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public ComplexObj getObject() throws Exception {
        return new ComplexObj();
    }

    @Override
    public Class<?> getObjectType() {
        return ComplexObj.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }
}
