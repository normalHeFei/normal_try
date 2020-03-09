package com.normal.trysth.concurrent;

/**
 *
 * @author hefei
 * @date 2017/9/13
 * 1. 可重入的优点： 锁可重入的目的是为了防止死锁的发生，如果父类的一个方法加锁，子类覆盖的话，如果锁不是可重入的，那就一直阻塞
 */
public class TestLock {
}
