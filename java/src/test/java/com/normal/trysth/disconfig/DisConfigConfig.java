package com.normal.trysth.disconfig;

import com.baidu.disconf.client.DisconfMgrBean;
import com.baidu.disconf.client.DisconfMgrBeanSecond;
import com.baidu.disconf.client.addons.properties.ReloadablePropertiesFactoryBean;
import com.baidu.disconf.client.addons.properties.ReloadingPropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author hefei
 * @date 2018/6/25
 */
@Configuration
public class DisConfigConfig {

    @Bean(destroyMethod = "destroy")
    public  DisconfMgrBean disconfMgrBean() {
        DisconfMgrBean config = new DisconfMgrBean();
        config.setScanPackage("com.normal.trysth.disconfig");
        return config;
    }

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public DisconfMgrBeanSecond disconfMgrBean2() {
        return new DisconfMgrBeanSecond();
    }

    @Bean
    public ReloadablePropertiesFactoryBean reloadablePropertiesFactoryBean() {
        ReloadablePropertiesFactoryBean factoryBean = new ReloadablePropertiesFactoryBean();
        factoryBean.setLocation("config.properties");
        return  factoryBean;
    }

    @Bean
    public ReloadingPropertyPlaceholderConfigurer configurer() {
        ReloadingPropertyPlaceholderConfigurer configurer = new ReloadingPropertyPlaceholderConfigurer();
        configurer.setIgnoreResourceNotFound(true);
        configurer.setIgnoreUnresolvablePlaceholders(true);
        configurer.setPropertiesArray(new Properties[]{});
        return  configurer;
    }
}

