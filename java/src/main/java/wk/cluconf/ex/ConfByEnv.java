package wk.cluconf.ex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author: fei.he
 */
@Component
public class ConfByEnv implements ConfProTest {

    @Autowired
    private Environment environment;


    @Override
    public String getProperty() {
        return environment.getProperty(key);
    }
}
