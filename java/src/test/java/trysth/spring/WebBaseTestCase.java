package trysth.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hf
 * @date 18-10-8
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = "com.normal.trysth.spring")
public class WebBaseTestCase {
    public static void main(String[] args) {
        SpringApplication.run(WebBaseTestCase.class, args);
    }

}
