package com.normal.trysth.concurrent;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * refer for: http://www.cnblogs.com/dolphin0520/p/3932921.html
 * Created by hefei on 2017/9/14.
 * 核心api:
 * 1.ThreadPoolExecutor  构造体参数备注：
 * 1.1： coreThreadNum: 虽然叫做核心线程数，但应该理解为线程池的大小，结合最大线程数说明
 * 1.2： maxThreadNum:  当核心线程数不够用的时候,才会根据最大线程数新建线程
 * 1.3： blockQueue:    阻塞队列，用于保存没有执行任务的runnable
 * 1.4: keepLiveTime:  不在核心线程中的线程最大存活时间
 */
public class TestThreadPool {

    @Test
    public void testFixedPool() throws InterruptedException, ExecutionException, TimeoutException {
        int coreTaskSize = 2, moreTaskSize = 10;
        ExecutorService service = Executors.newFixedThreadPool(coreTaskSize);
        List<Callable<Void>> tasks = new ArrayList<>();
        /*for (int i = 0; i < coreTaskSize + moreTaskSize; i++) {
            tasks.add(() -> {
                Thread.sleep(1000);
                return null;
            });
        }
        //阻塞至所有任务都执行完毕
        service.invokeAll(tasks);*/
        for (int i = 0; i < coreTaskSize + moreTaskSize; i++) {
            service.submit(() -> print(service));
        }
    }

    private void print(ExecutorService service) {
        ThreadPoolExecutor pool = (ThreadPoolExecutor) service;
        System.out.println(String.format("%d,%d,%d,%d,%d", pool.getCorePoolSize(), pool.getMaximumPoolSize(), pool.getPoolSize(), pool.getQueue().size(), pool.getTaskCount()));
    }

    @Test
    public void testCachePool() {
        ExecutorService service = Executors.newCachedThreadPool();
    }




}
