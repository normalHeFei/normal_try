package com.normal.trysth.transcation.distributeddemo.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * @author hf
 * @date 18-11-13
 */
@SpringBootApplication
@PropertySource("classpath:application-order.properties")
public class OrderMain {

    public static void main(String[] args) {
        SpringApplication.run(OrderMain.class, args);
    }

}
