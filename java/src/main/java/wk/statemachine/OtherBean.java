package wk.statemachine;

import org.springframework.stereotype.Component;

/**
 * @author: fei.he
 */
@Component
public class OtherBean {

    public void invoke() {
        System.out.println("invoke other bean");
    }
}
