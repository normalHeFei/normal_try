package trysth.concurrent;

import org.junit.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 *
 * @author normalhefei
 * @date 2017/6/12
 */
public class TestRefer {
    public static void main(String[] args) {
        //弱应用: gc 运行时会被回收，适用于对象容易造成内存溢出的场景，例如threadLocal中的ThreadLocalMap
        {
            Object strongRef = new Object();
            WeakReference<Object> weakRef = new WeakReference<>(strongRef);
            while (true) {
                if (weakRef.get() == null) {
                    System.out.println("obj refer by weakRefer has be collectd");
                    break;
                } else {
                    System.out.println(" still alive..");
                }
            }
        }
        //软引用： gc运行 并且内存不足时才会被回收，适用于缓存对象,
        //软引用相比于 弱应用更加难以回收
        /*{
            SoftReference<Object> softRef = new SoftReference<>(new Object());
            while (true) {
                if (softRef.get() == null) {
                    System.out.println("obj refer by softRef has be collected");
                    break;
                } else {
                    System.out.println(" still alive..");
                }
            }
        }*/
    }

    /**
     *
     */
    @Test
    public void testPhantomReference() {
        Object obj = new Object();
        ReferenceQueue queue = new ReferenceQueue();
        PhantomReference reference = new PhantomReference(obj, queue);
        System.out.println("obj:" + queue.poll());
        while (true) {
            if (queue.poll() != null) {
                System.out.println("had GC");
                break;
            }
            System.gc();
            System.out.println("not has GC , so not in queue");
        }


    }
}
