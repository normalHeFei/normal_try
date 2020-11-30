package trysth.spring.api;

import trysth.TestBase;
import trysth.objs.ComplexObj;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author hefei
 * @date 2018/6/25
 */
@SpringBootTest(classes = {Config.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TestMainApi extends TestBase {

    /**
     * bean 的构造复杂到xml 不能够描述的情况下，使用factoryBean
     * 如proxyFactoryBean
     * The FactoryBean interface is a point of pluggability into the Spring IoC container’s instantiation logic.
     * If you have complex initialization code that is better expressed in Java as opposed to a (potentially)
     * verbose amount of XML, you can create your own FactoryBean, write the complex initialization inside
     * that class, and then plug your custom FactoryBean into the container.
     */

    @Test
    public void testFactoryBean() throws Exception {

        ComplexObj bean = MyFactoryBean.getContext().getBean(ComplexObj.class);
        Assert.assertTrue(bean instanceof ComplexObj);

        MyFactoryBean myFactoryBean = (MyFactoryBean) MyFactoryBean.getContext().getBean("&myBean");
        ComplexObj object = myFactoryBean.getObject();
        Assert.assertTrue(object instanceof ComplexObj);
    }

    /**
     * BeanFactoryPostProcessor:bean 构造的后置处理器 用于自定义bean构造后的逻辑,如PlaceholderSupport就是修改bean属性
     * 与BeanPostProcessor 相比 作用范围更大, beanFactoryPostProcessor 是针对于任何一类bean
     * 而BeanPostProcessor 针对于特定的bean
     */
    @Test
    public void testBeanFactoryPostProcessor() {

    }


}
