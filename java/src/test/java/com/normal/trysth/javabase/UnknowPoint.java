package com.normal.trysth.javabase;

import org.junit.Test;

/**
 * Created by hefei on 2017/9/15.
 * loop sign 的运用场景：
 * 内存循环需要控制外层循环的情况
 */
public class UnknowPoint {
    //loop sign
    @Test
    public void testBreakSign() {
        retry:
        for (; ; ) {
            for (int i = 0; i < 10; i++) {
                if (i == 4) {
                    System.out.println("break retry");
                    break retry;        //跳出外层循环
                    // continue  retry;    //continue 外层循环
                }
            }
        }
    }
    //默认方法:
    //默认方法定义在接口中,为了支持lambda表达式而设,实现类可以不必实现.
}
