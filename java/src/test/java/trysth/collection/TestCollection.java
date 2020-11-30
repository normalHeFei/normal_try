package trysth.collection;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * Created by hefei on 2017/6/16.
 */
public class TestCollection {
    @Test
    public void testLinkedHashMap() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>(16, 0.75f, true);
        map.put("a", "avalue");
        map.put("b", "bvalue");
        map.get("a");
//        map.entrySet().forEach(entry -> System.out.println(entry.getValue()));
    }

    @Test
    public void testConcurrentHashMap() throws InterruptedException{
        CountDownLatch latch = new CountDownLatch(2);
        Map<String, String> map = new ConcurrentHashMap<>(1);
        PutValueThread  putA = new PutValueThread(map);
        PutValueThread  putB = new PutValueThread(map);
        latch.await();
    }


    @Test
    public void testSet() {
        Set<String> set = new HashSet<>();
        set.add(null);  // hashSet 无法允许空
        set = new TreeSet<>();
        set.add(null);  // 红黑树实现的set不允许空


    }

    static class PutValueThread implements Runnable{

        private final Map<String, String> map;

        public PutValueThread(Map<String, String> map) {
            this.map = map;

        }

        @Override
        public void run() {

        }
    }

}

