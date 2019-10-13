package com.normal.trysth.transcation.distributeddemo;

import com.normal.trysth.transcation.distributeddemo.order.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author hf
 * @date 18-11-13
 */
@SpringBootTest(classes = DistributedTransTest.class)
@PropertySource("classpath:application-order.properties")
@RunWith(SpringRunner.class)
@Configuration
@ComponentScan("com.normal.trysth.transcation.distributeddemo.order")
public class DistributedTransTest {


    @Autowired
    private OrderService orderService;


    @Test
    @Rollback(value = false)
    public void test() {
        orderService.addOrder("1", 50);
    }




}

