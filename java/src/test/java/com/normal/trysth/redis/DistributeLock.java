package com.normal.trysth.redis;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author hefei
 * @date 2017/8/30
 */
public interface DistributeLock {

    boolean tryLock(Long timeout, TimeUnit timeUnit);

    void lock();

    void unlock();


}