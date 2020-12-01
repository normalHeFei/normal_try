package com.normal.trysth.concurrent;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class TestCurrentModifyExe {

    public static void main(String[] args) {
        ArrayList<String> list = Lists.newArrayList("1","2","3","x");

        /**
         * 一个线程迭代
         */
        new Thread(){
            @Override
            public void run() {

                for (String s : list) {
                    System.out.println(Thread.currentThread().getName() + " 正在迭代");
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        /**
         * one thread do update
         */
        new Thread(){
            @Override
            public void run() {

                list.add("xxx");
            }
        }.start();

    }
}
