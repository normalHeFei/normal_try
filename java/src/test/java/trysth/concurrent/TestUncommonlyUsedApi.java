package trysth.concurrent;

import org.junit.Test;
import sun.reflect.Reflection;

public class TestUncommonlyUsedApi {

    @Test
    public void test() {
        System.out.println(Reflection.getCallerClass());
    }
}
