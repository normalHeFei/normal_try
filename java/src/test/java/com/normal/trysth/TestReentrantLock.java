package com.normal.trysth;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hf
 * @date 18-7-5
 */
public class TestReentrantLock {
    static class MethodReentrantLock {
        private ReentrantLock lock;

        public MethodReentrantLock(ReentrantLock lock) {
            this.lock = lock;
        }

        public void method1() {
            try {
                lock.lock();
                System.out.println("enter method 1");
                method2();
            }finally {
                lock.unlock();
            }
        }
        public void method2() {
            try {
                lock.lock();
                System.out.println("enter method 2");
            }finally {
                lock.unlock();
            }
        }
    }
    @Test
    public void test() {
        new MethodReentrantLock(new ReentrantLock()).method1();
    }
}
