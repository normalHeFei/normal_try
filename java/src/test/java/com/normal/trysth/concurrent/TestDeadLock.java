package com.normal.trysth.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * @author hf
 * @date 18-7-5
 */
public class TestDeadLock {
    private static Class<Integer> lockOne = Integer.class;
    private static Class<String> lockTwo = String.class;


    public void methodUseLockOneThenLockTwo() {
        synchronized (lockOne){
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName +  " begin try to get lock two");
                synchronized (lockTwo) {
                    System.out.println(threadName + " get lock two success");
                }
        }
    }

    public void methodUseLockTwoThenLockOne() {
        synchronized (lockTwo){
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName + " begin try to get lock One");
                synchronized (lockOne) {
                    System.out.println(threadName + " get lock one success");
                }
        }
    }

    public static void main(String[] args) {
        TestDeadLock testDeadLock = new TestDeadLock();
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(){
            @Override
            public void run() {
                try {
                    System.out.println(getName() + " is ready...");
                    latch.await();
                    testDeadLock.methodUseLockOneThenLockTwo();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                try {
                    System.out.println(getName() + " is ready...");
                    latch.await();
                    testDeadLock.methodUseLockTwoThenLockOne();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        latch.countDown();
    }
}
