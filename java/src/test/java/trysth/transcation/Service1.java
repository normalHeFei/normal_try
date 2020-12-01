package trysth.transcation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hefei on 2017/4/24.
 */
@Service
public class Service1 {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Service2 service2;

    private void write(String param) {
        jdbcTemplate.update("update temp set invokedNum =? ", new Object[]{param});
    }


    private List<String> valueInDbNow() {
        List<String> invokedNum = jdbcTemplate.query("select invokedNum from temp ", (resultSet, i) -> resultSet.getString("invokedNum"));
        return invokedNum;
    }

    public String read() {
        return valueInDbNow().toString();
    }


    @Transactional
    public void testRequiredNew() {
        write("write11");
        try {
            service2.writeUseRequiresNew();
        } catch (Exception e) {
            //ignore
        }
    }

    @Transactional
    public void testRequired() {
        write("write11");
        try {
            service2.writeUseRequired();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void testNested() {
        service2.writeUseNested();
        write("write111");
        throw new IllegalArgumentException();
    }

    @Transactional
    public void testInnerInvokedTransMethod() {
        //应该不能回滚
        innerTransMethod();
        System.out.println(read());
        //write 能回滚
        writeBaseUser("something write by invoked method");
        throw new IllegalArgumentException();
    }

    private void writeBaseUser(String sth) {
        jdbcTemplate.update("update base_user set name =? ", new Object[]{sth});
    }

    private void innerTransMethod() {
        write("something write by inner method");
    }


}
