package trysth.spring.ioc;

import trysth.TestBase;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 1.
 */
@SpringBootTest(classes = {Config.class},
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TestIoc extends TestBase {


    @Test
    public void testIocProcess() {
        /**
            1. 反射初始化 context
            2. 设置相关功能接口到 context, 如 conversionService (主要是一些beanFactory的一些属性)
            3. load class/resource/package 文件包装成可以构造成bean 的描述对象 (BeanDefinition)
            4. 将BeanDefinition 注册到map 中, BeanDefinitionRegistry
            5.
         */

    }


}
