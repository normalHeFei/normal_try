package com.normal.trysth.es;

import com.normal.core.es.EsClient;
import com.normal.core.es.IndexData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;

import javax.annotation.PostConstruct;

/**
 * @author hefei
 * @date 2018/6/26
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, WebMvcAutoConfiguration.class})
public class EsTest {

    public static void main(String[] args) {
        SpringApplication.run(EsTest.class, args);
    }

    @Autowired
    EsClient esClient;

    @PostConstruct
    public void doTest() {
        IndexData indexData = new IndexData();
        indexData.setId("blog_02");
        indexData.setIndex("blog");
        indexData.setType("comment");
        indexData.putSource("name", "xiaoming");
        esClient.createIndex(indexData);

    }

}
