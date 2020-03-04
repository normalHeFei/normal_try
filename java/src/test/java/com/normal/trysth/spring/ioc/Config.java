package com.normal.trysth.spring.ioc;

import com.normal.trysth.objs.SimpleBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {


    @Bean("simpleBean")
    public SimpleBean simpleBean() {
        return new SimpleBean();
    }
}
