package com.normal.trysth.transcation.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hf
 * @date 18-11-7
 */
@Service
public class ServiceA {

    @Autowired
    private Mapper mapper;


    @Transactional
    public void method() {
        mapper.update();
        throw new RuntimeException();
    }
}
