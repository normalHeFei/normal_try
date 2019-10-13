package com.normal.trysth;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author hf
 * @date 19-8-15
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BaseApp {

}
