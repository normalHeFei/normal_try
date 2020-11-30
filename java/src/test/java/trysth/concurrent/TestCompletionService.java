package trysth.concurrent;

import java.util.concurrent.*;

/**
 * refer from： http://www.jianshu.com/p/cfda708a3478
 * Created by hefei on 2017/7/28.
 *  ExecutorCompletionService, 相对于Executor可以保证后面的任务不受前面提交任务的影响
 *  Executor 每次提交后返回future，因为get操作是阻塞的，如果前面的任务一直不执行完，即使
 *  后面的任务已经执行完了，也拿不到结果。
 *
 *  实现机制：将提交的runnable 或 callable 封装成一个QueueingFuture (extend FutureTask) “阻塞队列任务"
 *  保证提交的任务中，只要有一个完成了，take 就能拿到结果
 */
public class TestCompletionService {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ExecutorCompletionService<String> service = new ExecutorCompletionService<>(executor);
        service.submit(() -> {
            Thread.sleep(3000);
            return "result1";
        });

        service.submit(()->{
            Thread.sleep(2000);
            return "result2";
        });

        int i = 0;
        while (i < 2) {
            Future<String> future = service.take();
            System.out.println(future.get());
            i++;
        }
        executor.shutdown();
    }

    

}