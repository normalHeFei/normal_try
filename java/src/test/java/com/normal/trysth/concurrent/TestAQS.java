package com.normal.trysth.concurrent;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hefei
 * @date 2018/7/6
 */
public class TestAQS {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock(false);
        for (int i=0;i<10;i++) {
            new MultiThreadExeMethodBody(lock, "thread" + i)
                    .start();
        }
    }


    //多个线程执行的需要同步的方法块
    static class MultiThreadExeMethodBody extends Thread{
        private ReentrantLock lock;

        public MultiThreadExeMethodBody(ReentrantLock lock,String name) {
            super(name);
            this.lock = lock;
        }

        @Override
        public void run() {
            exec();
        }

        public void exec() {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " is sleeping...");
                Thread.sleep(3000L);
                System.out.println(Thread.currentThread().getName() + "  wake up");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }
}
