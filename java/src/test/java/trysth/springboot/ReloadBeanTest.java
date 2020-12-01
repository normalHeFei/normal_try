package trysth.springboot;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: fei.he
 */
public class ReloadBeanTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-test.xml");

        ReloadBean bean = context.getBean(ReloadBean.class);

        System.out.println("原先值: " +  bean.getValue() + " 原先对象: " + bean);

        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        beanFactory.destroyBean(bean);

        ReloadBean singletonObject = new ReloadBean();
        singletonObject.setValue("222");
        beanFactory.registerSingleton("bean", singletonObject);


        ReloadBean reloadBean = context.getBean(ReloadBean.class);

        System.out.println("更新后的值: " +  reloadBean.getValue() + " 更新后对象: " + bean);
    }
}
