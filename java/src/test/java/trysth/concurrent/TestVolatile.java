package com.normal.trysth.concurrent;

import java.util.concurrent.TimeUnit;

public class TestVolatile {
    volatile int num;


    public static void main(String[] args) {
        TestVolatile testVolatile = new TestVolatile();
        new Thread(){
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " is coming in ");
                try {
                    Thread.sleep(TimeUnit.SECONDS.toMillis(3));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                testVolatile.num = 2;
                System.out.println(Thread.currentThread().getName() + " set num = " + testVolatile.num);
            }
        }.start();


        for (; testVolatile.num == 0; ) {

        }

        System.out.println("线程可见");
    }
}

