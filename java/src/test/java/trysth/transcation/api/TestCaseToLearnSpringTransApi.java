package trysth.transcation.api;

import trysth.MixAll;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author hf
 * @date 18-11-7
 */
@SpringBootTest(classes = {MixAll.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestCaseToLearnSpringTransApi {

    @Autowired
    private ServiceA serviceA;

    @Test
    @Rollback(false)
    public void testSpringTransApi() {
        serviceA.method();
    }
}
