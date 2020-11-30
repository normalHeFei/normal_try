package trysth.concurrent;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @author: fei.he
 */
public class TestCyclicBarrier {

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(5);
        for (int i = 0; i < 5; i++){
            new RowThread(barrier).start();
        }
    }
}

class RowThread extends Thread {

    private CyclicBarrier barrier;


    public RowThread(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        Random random = new Random();
        int nextInt = random.nextInt(10);
        String threadName = Thread.currentThread().getName();
        try {
            System.out.println(threadName + " will waiting " + nextInt + " seconds");
            Thread.sleep(TimeUnit.SECONDS.toMillis(nextInt));
            barrier.await();
            System.out.println(threadName + " release, begin exe");

        } catch (InterruptedException e) {

        } catch (BrokenBarrierException e) {

        }

    }
}
