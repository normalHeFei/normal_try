package com.normal.trysth.concurrent;

import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 1. copy on write 无法保持实时一致性, 只能保持最终一致性.
 * 2. 数组数量太大的时候,占用内存多, 可能会导致 full gc
 * 3. 多线程修改集合时会报 ConcurrentModificationException, 如一个迭代, 一个删除. 该异常不能保证这种情况下一定throw
 */
public class TestCopyOnWrite {

    @Test
    public void test() {
        List<String> list = Arrays.asList("1", "2", "3", "4");
        Iterator<String> iterator = list.iterator();

        new Thread() {
            @Override
            public void run() {
                for (; iterator.hasNext(); ) {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                        iterator.next();
                    } catch (InterruptedException e) {

                    }
                }

            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                list.remove(1);
            }
        }.start();


        System.out.println(list.size());
    }


}
