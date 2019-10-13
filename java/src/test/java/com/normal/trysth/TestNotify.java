package com.normal.trysth;

/**
 * @author hefei
 * @date 2018/6/19
 * result:
 * 1. thread a invoke wait method, other thread like thread b can enter syn block
 * 2. thread a invoke notifyAll method & thread a come out syn block , other thread like b can enter syn block
 * note in :
 * notifyAll & wait 方法都需要在 同一把锁 包含的同步块中. 执行wait 方法 其他线程可以进入到同步块
 */
public class TestNotify {

    public static void main(String[] args) {
        Object objLock = new Object();
        //new ThreadATestWait(objLock).start();
        //new ThreadBTestWait(objLock).start();
        new ThreadPrintOneByOne("threadA", objLock).start();
        new ThreadPrintOneByOne("threadB", objLock).start();
    }


    //print  one by one
    static class ThreadPrintOneByOne extends Thread {
        private Object lockObj;

        public ThreadPrintOneByOne(String name, Object lockObj) {
            super(name);
            this.lockObj = lockObj;
        }

        @Override
        public void run() {
            while (true) {
                String name = Thread.currentThread().getName();
                synchronized (lockObj) {
                    System.out.println(name + " print...");
                    lockObj.notifyAll();
                    try {
                        lockObj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
