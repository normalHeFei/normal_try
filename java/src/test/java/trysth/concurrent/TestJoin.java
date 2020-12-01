package trysth.concurrent;

/**
 * @author: fei.he
 */
public class TestJoin {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {

                }
            }
        };
        thread.start();
        Thread.sleep(2000);
        System.out.println("2 second");
        //join 会阻塞
        thread.join();
        System.out.println("5 second");
    }


}
