package wk.cluconf.solve;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import wk.cluconf.ex.ConfProTest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author: fei.he
 */
@Configuration
@ComponentScan(basePackages = {"wk.cluconf.ex"})
public class ConfigTest implements CommandLineRunner {


    public static void main(String[] args) {
        new SpringApplicationBuilder().web(WebApplicationType.NONE)
                .sources(ConfigTest.class)
                .run(args);
    }

    @Autowired
    private List<ConfProTest> confProTests;

    @Autowired
    private ApplicationContext applicationContext;


    @Bean
    public AutoUpdatePropertySource autoUpdatePropertySource(ConfigurableEnvironment environment) {
        MutablePropertySources propertySources = environment.getPropertySources();
        Iterator<PropertySource<?>> iterator = propertySources.iterator();
        Map map = new HashMap(32);
        for (; iterator.hasNext(); ) {
            PropertySource<?> next = iterator.next();
            if (next.getSource() instanceof Map) {
                map.putAll((Map) next.getSource());
            }
        }
        AutoUpdatePropertySource autoUpdatePropertySource = new AutoUpdatePropertySource("autoUpdatePropertySource", map);
        propertySources.addFirst(autoUpdatePropertySource);
        return autoUpdatePropertySource;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("-----before----");
        for (ConfProTest confProTest : confProTests) {
            System.out.println(confProTest.getClass().getName() + " " + confProTest.getProperty());
        }

        System.out.println("-----after----");
        applicationContext.publishEvent(PropertyUpdateParam.newUpdateParam("xx", "zz"));
        for (ConfProTest confProTest : confProTests) {
            System.out.println(confProTest.getClass().getName() + " " + confProTest.getProperty());
        }
    }
}
