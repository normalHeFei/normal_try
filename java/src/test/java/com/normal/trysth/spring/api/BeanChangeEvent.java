package com.normal.trysth.spring.api;

import org.springframework.context.ApplicationEvent;

/**
 * @author hefei
 * @date 2018/6/25
 */
public class BeanChangeEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public BeanChangeEvent(Object source) {
        super(source);
    }
}
