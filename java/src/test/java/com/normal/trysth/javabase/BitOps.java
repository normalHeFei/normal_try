package com.normal.trysth.javabase;

/**
 *
 * @author normalhefei
 * @date 2017/3/26
 * 应用场景：
 * 1. 权限控制，如linux中的文件权限
 * 2. 2的几次方，效率相对较高
 */
public class BitOps {
    public static void main(String[] args) {
        // ~: 取反  0变1 ,1变0
        System.out.println((~90) == -91);
        // << 左移动： x<<y = x*2^y
        System.out.println(4<<3);
        // |: 或， 二进制所在位，只要有一个1，就为1
        // 正数x右移y位, result = (x-y)/2
        System.out.println((4>>2));
    }
}
