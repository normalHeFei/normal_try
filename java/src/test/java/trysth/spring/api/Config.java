package trysth.spring.api;

import trysth.objs.SimpleBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hefei on 2017/9/20.
 */
@Configuration
public class Config {
    @Bean("myBean")
    public MyFactoryBean factoryBean() {
        return new MyFactoryBean();
    }

    @Bean("simpleBean")
    public SimpleBean simpleBean() {
        return new SimpleBean();
    }
}
