package com.normal.trysth.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hefei on 2017/4/8.
 */
public class Oom {
    private static byte[] oneM = new byte[1024];

    public static void main(String[] args) {
        //堆溢出
        List<byte[]> heapOom = new ArrayList<>();
        while (true) {
            heapOom.add(oneM);
        }
    }
}
