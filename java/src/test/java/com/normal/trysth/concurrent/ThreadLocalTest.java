package com.normal.trysth.concurrent;

import java.lang.ref.WeakReference;

/**
 * Created by normalhefei on 2017/6/12.
 * refer from http://www.cnblogs.com/onlywujun/p/3524675.html
 * 每个线程都包含一个map，这个map的key 是threadLocal 的弱引用，value是真正设置的值
 * 内存泄漏的问题发生在
 * 1. 线程池的情况，线程用完未回收，设置的value值也一直存在 在内存中
 */
public class ThreadLocalTest {
    public static void main(String[] args) {
        ThreadLocal<String> local = new ThreadLocal<>();
        local.set("这里是内容");
        System.out.println(local.get());
        local.remove();
        //线程变量的弱引用作为map（当前线程维护的ThreadLocalMap）的key，
        //所以存在两个引用链如下(...>代表弱引用)：
        //1. 当前线程对象->ThreadLocalMap-> Entry<weakReference<ThreadLocal>,Object>
        //2. Entry(key) ...>  ThreadLocal refer(就比如上面的local)
        // map 持有的弱应用（key）会被回收，但map的value在线程没有运行结束的时候（特别是线程池的情况下）回收不了，会导致溢出，设置取值后，需要手动remove掉

        Value strongRefe = new Value("value");
        WeakReference<Value> weakRefer = new WeakReference<>(strongRefe);
        System.out.println(weakRefer.get());
        strongRefe = null;
        while (true) {
            if (weakRefer.get() == null) {
                System.out.println("value 已经被回收");
                break;
            }
        }

    }
    static class Value{
        private String name;

        public Value(String name) {
            this.name = name;
        }
    }
}
