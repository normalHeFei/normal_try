package com.normal.trysth.concurrent;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class TestAQS {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock(false);
        for (int i = 0; i < 10; i++) {
            new MultiThreadExeMethodBody(lock, "thread" + i)
                    .start();
        }
    }


    //多个线程执行的需要同步的方法块
    static class MultiThreadExeMethodBody extends Thread {
        private ReentrantLock lock;

        public MultiThreadExeMethodBody(ReentrantLock lock, String name) {
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
            } finally {
                lock.unlock();
            }
        }
    }

    static class Node {
        volatile boolean lock;
    }

    static class CLHLock {
        private ThreadLocal<Node> pre =  new ThreadLocal<>();
        private ThreadLocal<Node> curr = ThreadLocal.withInitial(Node::new);
        private AtomicReference<Node> tail = new AtomicReference<>(new Node());

        public CLHLock() {
        }

        public void lock() {
            Node curr = this.curr.get();
            //设置当前节点状态
            curr.lock = true;
            //队尾指针 指向新进来的线程对应的当前节点
            //tail get 返回的是之前阻塞的节点
            Node preLockNode = tail.getAndSet(curr);
            //当前节点的前置节点设置为之前的阻塞节点
            pre.set(preLockNode);
            //根据之前节点的状态自旋
            for (; pre.get().lock ; ) {

            }
        }

        public void unlock() {
            this.curr.get().lock = false;
            //已解锁节点 出队列?
            curr.set(pre.get());
        }
    }

    @Test
    public void testLockSupport() {
        System.out.println(LocalDateTime.now());
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(10));
        System.out.println(LocalDateTime.now());
    }
}
