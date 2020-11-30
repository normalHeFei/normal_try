package trysth.concurrent;

import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
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
        private ThreadLocal<Node> pre = new ThreadLocal<>();
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
            for (; pre.get().lock; ) {

            }
        }

        public void unlock() {
            this.curr.get().lock = false;
            //已解锁节点 出队列?
            curr.set(pre.get());
        }
    }

    /**
     * AQS 模拟
     */
    static class MyAQS {

        static final class Node {
            /**
             * cancel: -1,
             */
            volatile int waitStatus;

            volatile Node pre;

            volatile Node next;

            volatile Thread thread;


            public Node(Thread currentThread) {
                this.thread = currentThread;
            }


        }

        volatile Node head;
        volatile Node tail;

        private static Unsafe unsafe;


        static {
            try {
                Field field = Unsafe.class.getDeclaredField("theUnsafe");
                field.setAccessible(true);
                unsafe = (Unsafe) field.get(null);


            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }


        public boolean tryAcquire(int status) {
            throw new UnsupportedOperationException();
        }

        private void acquire(int status) {
            if (!tryAcquire(status)) {
                boolean interrupted = tryAcquireQueue(addWaiter(new Node(Thread.currentThread())));
                if (interrupted) {
                    //恢复中断
                    Thread.currentThread().interrupt();
                }
            }
        }

        private boolean tryAcquireQueue(Node addWaiter) {
            return true;
        }

        /**
         * ensure add queue
         *
         * @param node
         */
        private Node addWaiter(Node node) {
            for (; ; ) {
                Node pre = tail;
                if (pre == null) {
                    if (compareAndSetHead(node)) {
                        tail = head;
                    }
                } else {
                    //悲观设置当前node 为队尾.
                    compareAndSetTail(pre, node);
                    pre.next = node;
                    node.pre = pre;
                    return node;
                }
            }
        }

        private void compareAndSetTail(Node preTail, Node node) {

        }

        private boolean compareAndSetHead(Node node) {
            return false;
        }


    }

    @Test
    public void testLockSupport() {
        System.out.println(LocalDateTime.now());
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(10));
        System.out.println(LocalDateTime.now());
    }
}
