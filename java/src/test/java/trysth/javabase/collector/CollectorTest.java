package trysth.javabase.collector;

import org.junit.Test;

import java.util.function.ObjDoubleConsumer;

/**
 * @author hefei
 * @date 2017/9/14
 * 收集器
 * refer for:
 * http://blog.csdn.net/wzq6578702/article/details/66477458
 *
 */
public class CollectorTest {

    @Test
    public void testBiConsumer() {
        ObjDoubleConsumer equals = Object::equals;
        equals.accept("a", Double.parseDouble("2.4"));
    }




}
