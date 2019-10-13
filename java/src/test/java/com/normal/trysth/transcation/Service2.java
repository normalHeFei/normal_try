package com.normal.trysth.transcation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hefei on 2017/4/24.
 */
@Service
public class Service2 {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String read() {
        return valueInDbNow().toString();
    }

    private void write(String param) {
        jdbcTemplate.update("update base_user set id =? ", new Object[]{param});
    }


    private List<String> valueInDbNow() {
        List<String> invokedNum = jdbcTemplate.query("select invokedNum from temp ", (resultSet, i) -> resultSet.getString("invokedNum"));
        return invokedNum;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void writeUseRequiresNew() {
        write("write22");
        throw new IllegalArgumentException("");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void writeUseRequired() {
        write("write22");
        throw new IllegalArgumentException("");
    }
    @Transactional(propagation = Propagation.NESTED)
    public void writeUseNested() {
        write("write2");
//        throw new IllegalArgumentException("");
    }
}
