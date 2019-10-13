package com.normal.trysth.mock;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@RestController
public class ExtjsMockDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExtjsMockDataApplication.class);

    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

    @RequestMapping("/extJsMockData")
    public Object mockData() {
        location city = new location();
        city.country = "china";
        city.city = "hangzhou";
        User user = new User();
        user.name = "sdf";
        user.age = 99;
        user.birthday = LocalDate.of(1991, 12, 5);
        user.location = city;
        user.gender = Gender.MAN;
        return Arrays.asList(user);
    }

    @RequestMapping("/mockMenuData")
    public Object mockMenuData() throws IOException {
        ObjectMapper om = new ObjectMapper();
        List list = om.readValue(new File("F:\\normal-all\\normal-try\\src\\test\\resources\\data.json"), List.class);
        return list;
    }


    @Data
    static class User {
        String name;
        int age;
        LocalDate birthday;
        Gender gender;
        location location;
    }

    @Data
    static class location {
        String country;
        String city;
    }

    enum Gender {

        MAN("1", "男"),
        WOMEN("2", "女"),
        ;

        private final String key;
        private final String value;

        Gender(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @JsonValue
        public Map<String, String> toValue() {
            Map<String, String> map = new HashMap<>();
            map.put("key", this.key);
            map.put("value", this.value);
            return map;
        }
    }


}
