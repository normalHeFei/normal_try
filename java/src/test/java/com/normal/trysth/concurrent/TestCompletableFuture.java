package com.normal.trysth.concurrent;

import java.util.concurrent.CompletableFuture;

/**
 * @author hf
 * @date 18-10-9
 */
public class TestCompletableFuture {

    public static void main(String[] args) throws Throwable {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 1);
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> 2);

        future1.thenAcceptBothAsync(future2, (i1, i2) -> {
            System.out.println(i1 + i2);
        });

        //combine both result to final result
        CompletableFuture<String> result = future1.thenCombine(future2, (i1, i2) -> i1 + " " + i2);
        result.thenAccept(System.out::print);
        //xmlconverter
        future1.thenCompose((i1) -> CompletableFuture.supplyAsync(() -> i1)).thenAccept(System.out::print);

        //trysth cost time
        //testCostTime();


    }


    public static void testCostTime() throws InterruptedException {
        long start = System.currentTimeMillis();
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {

            }
            return 1;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {

            }
            return 2;
        }), (i1, i2) -> i1 + i2)
                .thenAccept((result) -> {
                    System.out.println(result);
                    System.out.println(System.currentTimeMillis() - start);
                });

        //wait to print sub thread result
        //Thread.sleep(10000L);
    }

}

