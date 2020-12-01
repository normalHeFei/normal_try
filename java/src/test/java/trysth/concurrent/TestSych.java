package trysth.concurrent;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * Created by hefei on 2017/6/19.
 */
public class TestSych {
    ExecutorService service = Executors.newFixedThreadPool(5);

    @Test
    public void testFutrueTask() throws Throwable {
        {
            long begin = System.currentTimeMillis();
            ExecutorService executorService = Executors.newFixedThreadPool(2);
            Future<?> future = executorService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    Thread.sleep(1000);
                    return "返回内容1";
                }
            });
            Future<?> future1 = executorService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    Thread.sleep(1000);
                    return "返回内容2";
                }
            });
            future.get();
            future1.get();
            long end = System.currentTimeMillis();
            System.out.println(end - begin);
        }
        {
            long begin = System.currentTimeMillis();
            Thread.sleep(1000);
            Thread.sleep(1000);
            long end = System.currentTimeMillis();
            System.out.println(end - begin);
        }
    }


    @Test
    public void testExecutorsManyTimes() {
        for (int i = 0; i < 1; i++) {
            testExecutors();
        }
    }

    public void testExecutors() {
        service.submit(() -> {
            Thread.sleep(3000);
            return "whatever";
        });
        service.shutdown();
    }

    //synchronized 包含一个锁对象，修饰方法时，这个锁对象是this；修饰同步块时，需要显式的指定锁对象
    //同一个锁对象修饰的方法(同步块)，在同一个时刻只有一个线程可以访问，意味着线程a,b同时访问method1,method2,在a访问method1的时候，b不能访问method2；同理 b访问method2的时候，a不能访问method1
    //如果method1 和 method2 的锁对象不同时，那么这时是可以被多个线程同时访问的
    public static void main(String[] args) throws Throwable {
        CountDownLatch startCountDown = new CountDownLatch(1);
        MulitMethodForSyn methods = new MulitMethodForSyn();
        ThreadInvokeMethod threadInvokeMethod1 = new ThreadInvokeMethod(startCountDown, methods, "method1");
        ThreadInvokeMethod threadInvokeMethod2 = new ThreadInvokeMethod(startCountDown, methods, "method2");
        threadInvokeMethod1.start();
        threadInvokeMethod2.start();
        Thread.sleep(2000);
        startCountDown.countDown();
    }


}

class MulitMethodForSyn {


    synchronized public void method1() {
        try {
            System.out.println("method1 running");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized public void method2() {
        try {
            System.out.println("method2 running");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* public void method2() {
         synchronized (MulitMethodForSyn.class) {
             System.out.println("method2 running");
             try {
                 Thread.sleep(2000);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
     }*/

}

class ThreadInvokeMethod extends Thread {
    private final CountDownLatch startCountDown;
    private final MulitMethodForSyn methodForSyn;
    private final String invokedMethodName;

    public ThreadInvokeMethod(CountDownLatch startCountDown, MulitMethodForSyn methodForSyn, String invokedMethodName) {
        this.startCountDown = startCountDown;
        this.methodForSyn = methodForSyn;
        this.invokedMethodName = invokedMethodName;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " is ready,method name:" + this.invokedMethodName);
            startCountDown.await();
            if (invokedMethodName.equals("method1")) {
                methodForSyn.method1();
            } else {
                methodForSyn.method2();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
