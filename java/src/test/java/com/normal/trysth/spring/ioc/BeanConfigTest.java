package com.normal.trysth.spring.ioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hefei on 2017/9/20.
 */
@Configuration
public class BeanConfigTest {
    @Bean("myBean")
    public MyFactoryBean factoryBean() {
        return new MyFactoryBean();
    }
}
