package com.normal.trysth.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by hefei on 2017/8/25.
 * 作用：用来标记受限资源
 */
public class TestSemaphore {

    public static void main(String[] args) {
        int bound = 2;
        BoundedList list = new BoundedList(bound);
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            new Thread() {
                @Override
                public void run() {
                    try {
                        list.add(String.valueOf(finalI));
                    } catch (InterruptedException e) {
                        //被中断的话，说明没有加上元素

                    }
                }
            }.start();
        }
    }

    static class BoundedList {
        private final List<String> innerList;
        private final Semaphore semaphore;

        public BoundedList(int bound) {
            this.innerList = new ArrayList<>();
            this.semaphore = new Semaphore(bound, true);
        }

        public boolean add(String value) throws InterruptedException {
            semaphore.acquire();
            return innerList.add(value);
        }

        public boolean remove(String value) {
            semaphore.release();
            return innerList.remove(value);
        }


        public int size() {
            return innerList.size();
        }
    }
}
