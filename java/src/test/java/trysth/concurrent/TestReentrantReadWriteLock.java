package trysth.concurrent;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author hefei
 * @date 2018/7/10
 */
public class TestReentrantReadWriteLock {
    private static ReentrantReadWriteLock rwl =  new ReentrantReadWriteLock();

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
//        testRead();
//        testWrite();
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);
        unsafe.compareAndSwapInt(new Object(), 2L, 1, 2);
    }


    public static void testRead() {
        ReadWriteLockProtectedData data = new ReadWriteLockProtectedData(rwl.readLock(), rwl.writeLock(), Lists.newArrayList("1", "2"));
        for (int i = 0; i < 10; i++) {
            new  Thread(){
                @Override
                public void run() {
                    try {
                        data.read();
                    } catch (InterruptedException e) {

                    }
                }
            }.start();
        }
    }
    public static void testWrite() {
        ReadWriteLockProtectedData data = new ReadWriteLockProtectedData(rwl.readLock(), rwl.writeLock(), Lists.newArrayList("1", "2"));
        for (int i = 0; i < 10; i++) {
            new  Thread(){
                @Override
                public void run() {
                    try {
                        data.add("1");
                    } catch (InterruptedException e) {

                    }
                }
            }.start();
        }
    }


    static class ReadWriteLockProtectedData {
        ReentrantReadWriteLock.ReadLock readLock;
        ReentrantReadWriteLock.WriteLock writeLock;
        ArrayList<String> data;

        public ReadWriteLockProtectedData(ReentrantReadWriteLock.ReadLock readLock, ReentrantReadWriteLock.WriteLock writeLock, ArrayList<String> data) {
            this.readLock = readLock;
            this.writeLock = writeLock;
            this.data = data;
        }

        public String read() throws InterruptedException {
            String item = null;
            try {
                readLock.lock();
                System.out.println(Thread.currentThread().getName() + " 正在读...");
                Iterator<String> iterator = data.iterator();
                for (; iterator.hasNext(); ) {
                    item = iterator.next();
                    break;
                }
                Thread.sleep(2000L);
            } finally {
                if (Strings.isNullOrEmpty(item)) {
                    System.out.println(Thread.currentThread().getName() + " 没读到..");
                } else {
                    System.out.println(Thread.currentThread().getName() + " 读到的值为" + item);
                }
                readLock.unlock();
            }
            return item;
        }

        public void add(String item) throws InterruptedException {
            try {
                writeLock.lock();
                System.out.println(Thread.currentThread().getName() + " 正在写...");
                Thread.sleep(2000L);
            } finally {
                writeLock.unlock();
                System.out.println(Thread.currentThread().getName() + " 写成功");
            }
        }

    }


}
