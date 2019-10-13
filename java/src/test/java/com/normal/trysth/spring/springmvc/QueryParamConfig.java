package com.normal.trysth.spring.springmvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by hefei on 2017/8/26.
 */
@Configuration
public class QueryParamConfig extends WebMvcConfigurerAdapter {
    private static Logger logger = LoggerFactory.getLogger(QueryParamConfig.class);

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new QueryParamArgumentResolver());
    }

}
