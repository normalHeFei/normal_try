package trysth.concurrent;

import java.io.File;

/**
 * Created by hefei on 2017/9/11.
 * 关于InterruptedException如何处理：
 * refer from: http://www.infoq.com/cn/articles/java-interrupt-mechanism
 * 1.一个方法签名中包含中断异常的方法，意味着执行这个方法所在的线程可能会被通知中断
 * 2.要不要吞掉中断异常，取决于中断异常对于这个 调用中断方法的代码 是否有价值，如果这个调用端代码
 * 需要利用中断信息做相应的处理，那么就不能吞掉异常
 * 3.什么时候才需要调用interrupt 方法？
 * 除中断以外，其他场景不应该调用中断方法。对于被中断的代码而言，捕捉异常的时候应该是知道这个中断意味着什么的
 * 也就是说触发中断异常都是事先知道的。
 * 3.1 利用中断机制来中断线程时应该注意，捕捉中断异常的代码要排除阻塞的风险，因为如果阻塞了的话，就永远
 * 得不到机会来执行isInterrupted 方法了，也就永远也得不到中断
 * <p>
 * 4.几个重要的方法：
 * interrupted：返回当前线程中断状态，并清除中断状态，也就是说如果第一次调用这个方法返回中断状态是true，第二次再调
 * 用这个方法就会返回false，因为这个方法本身就有清除中断状态的作用
 * isInterrupted: 仅仅返回当前线程中断状态
 * interrupt： 中断这个方法所属的线程。
 * <p>
 * 5.非标准的线程中断：socket 连接，nio，利用socketException来实现线程中断效果，可以通过
 * 6.对于需要用到中断来管理线程生命周期的场景，应该利用future，executorService来实现
 */
public class TestInterrupt {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("当前线程名：" + Thread.currentThread().getName());
        TaskCostTime fileScanner = new TaskCostTime(new File("f:/"));
        fileScanner.start();
        Thread.sleep(2000);
        fileScanner.interrupt();
    }

}

class TaskCostTime extends Thread {
    private File root;

    public TaskCostTime(File root) {
        this.root = root;
    }

    @Override
    public void run() {
        listFiles(root);

    }

    private void listFiles(File root) {
        if (isInterrupted()) {
            System.out.println(Thread.currentThread().getName() + "had been interrupted");
            return;
        }
        if (!root.isDirectory()) {
            System.out.println(root.getName());
            return;
        }
        File[] childern = root.listFiles();
        if (childern == null) {
            return;
        }
        for (File child : childern) {
            listFiles(child);
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
    }


}
