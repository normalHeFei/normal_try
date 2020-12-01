package trysth.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hefei
 * @date 2018/7/6
 * 可以这么理解:
 * condition 的存在是对 "被锁包围的代码,同一时间只有一个线程可以访问"的一种破坏
 * condition 一定是在Thread类内部使用的
 * 当condition.signal 的时候,被condition.await 阻塞的代码就会被唤醒.重新执行
 * 当condition.await 的时候,另外的线程就可以进入同步块.
 * 这个和Object.await & Object.notifyAll所起的作用是一样的.
 */
public class TestConditonAndReentrantLock {

    static class BoundedCollection {
        private final Object[] array;
        private int currIndex = -1;
        private final int maxSize;
        private final ReentrantLock lock = new ReentrantLock();
        private Condition canPut = lock.newCondition();
        private Condition canGet = lock.newCondition();

        public BoundedCollection(int maxSize) {
            this.array = new Object[maxSize];
            this.maxSize = maxSize;
        }

        public void put(Object item) throws InterruptedException {
            lock.lock();
            try {
                for (; currIndex == maxSize - 1; ) {
                    System.out.println(Thread.currentThread().getName() + "数组已满,设值等待,当前index:" + currIndex);
                    canPut.await();
                }
                array[++currIndex] = item;
                canGet.signal();
                System.out.println(Thread.currentThread().getName() + "设值成功 " + item + ",通知取值");
            } finally {
                lock.unlock();
            }
        }

        public Object get() throws InterruptedException {
            lock.lock();
            try {
                for (; currIndex < 0; ) {
                    System.out.println(Thread.currentThread().getName() + "数组已空,取值等待,当前index:" + currIndex);
                    canGet.await();
                }
                Object item = array[currIndex];
                currIndex--;
                canPut.signal();
                System.out.println(Thread.currentThread().getName() + "取值成功 " + item + ",通知设值");
                return item;
            } finally {
                lock.unlock();
            }
        }
    }

    static class ProductorThread extends Thread {
        private BoundedCollection shareCollection;
        private final Random random = new Random();

        public ProductorThread(BoundedCollection shareCollection) {
            this.shareCollection = shareCollection;
        }

        @Override
        public void run() {
            try {
                shareCollection.put(random.nextInt(100));
            } catch (InterruptedException e) {

            }
        }
    }

    static class ConsumerThread extends Thread {
        private BoundedCollection shareCollection;

        public ConsumerThread(BoundedCollection shareCollection) {
            this.shareCollection = shareCollection;
        }

        @Override
        public void run() {
            try {
                shareCollection.get();
            } catch (InterruptedException e) {

            }
        }
    }

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(10);
        BoundedCollection shareCollection = new BoundedCollection(5);

        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            if (random.nextInt(10) % 2 == 0) {
                new ProductorThread(shareCollection).start();
            } else {
                new ConsumerThread(shareCollection).start();
            }
        }
    }

}
