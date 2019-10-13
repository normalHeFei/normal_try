package com.normal.trysth.disconfig;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author hefei
 * @date 2018/6/25
 */
@Service
@DisconfFile(filename = "application.properties")
public class ServiceUseDisconfig {
    private static final Logger logger = LoggerFactory.getLogger(ServiceUseDisconfig.class);
    private String  redisHost;

    @DisconfFileItem(name = "redis.host",associateField = "redisHost")
    public String getRedisHost() {
        return redisHost;
    }

    public void setRedisHost(String redisHost) {
        logger.info("设置了redis host");
        this.redisHost = redisHost;
    }
}
