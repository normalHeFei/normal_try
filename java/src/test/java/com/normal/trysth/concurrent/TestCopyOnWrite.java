package com.normal.trysth.concurrent;

import org.junit.Test;

/**
 * 1. System.arraycopy, 修改内部数组引用实现
 * 2. 写加锁， 读未加锁
 * 3. 不能保证实时一致性，像size 等方法无法保证实时正确
 * 4. 适用于 array 规模不大的情况，每次写都copy 消耗大
 */
public class TestCopyOnWrite {

    



}
