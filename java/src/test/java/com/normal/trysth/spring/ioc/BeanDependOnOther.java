package com.normal.trysth.spring.ioc;

import com.normal.trysth.objs.SimpleBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeanDependOnOther {

    SimpleBean simpleBean;

    @Autowired
    public void setSimpleBean(SimpleBean simpleBean) {
        this.simpleBean = simpleBean;
    }
}
