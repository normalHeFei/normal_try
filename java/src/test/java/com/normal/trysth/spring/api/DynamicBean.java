package com.normal.trysth.spring.api;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * @author hefei
 * @date 2018/6/25
 */
public class DynamicBean implements Watcher {
    private String configValue;

    @Override
    public void process(WatchedEvent watchedEvent) {
        String configValue = watchedEvent.getPath();
        setConfigValue(configValue);
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }
}
