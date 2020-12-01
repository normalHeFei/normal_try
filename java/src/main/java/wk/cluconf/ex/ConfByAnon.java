package wk.cluconf.ex;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: fei.he
 */
@Component
public class ConfByAnon implements ConfProTest {

    @Value("${xx}")
    private String value;


    @Override
    public String getProperty() {
        return value;
    }
}
