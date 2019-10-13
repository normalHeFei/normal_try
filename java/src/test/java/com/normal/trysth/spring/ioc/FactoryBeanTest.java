package com.normal.trysth.spring.ioc;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author hefei
 * @date 2017/9/20
 * bean 的构造复杂到xml 不能够描述的情况下，使用factoryBean
 * 如proxyFactoryBean
 * The FactoryBean interface is a point of pluggability into the Spring IoC container’s instantiation logic.
 * If you have complex initialization code that is better expressed in Java as opposed to a (potentially)
 * verbose amount of XML, you can create your own FactoryBean, write the complex initialization inside
 * that class, and then plug your custom FactoryBean into the container.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {BeanConfigTest.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class FactoryBeanTest {
    @Test
    public void testFactoryBean() throws Exception {

        ComplexObj bean = MyFactoryBean.getContext().getBean(ComplexObj.class);
        Assert.assertTrue(bean instanceof ComplexObj);

        MyFactoryBean myFactoryBean = (MyFactoryBean) MyFactoryBean.getContext().getBean("&myBean");
        ComplexObj object = myFactoryBean.getObject();
        Assert.assertTrue(object instanceof ComplexObj);
    }

    

}
