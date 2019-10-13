package com.normal.trysth.javabase;

/**
 * Created by hefei on 2017/9/13.
 */
public class Finally {

    public static void main(String[] args) {
        try {
            throw new IllegalStateException("whatever");
        }finally {
            System.out.println("就算抛异常，还是执行了");
        }
    }
}
