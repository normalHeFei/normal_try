package com.normal.trysth.redis;


import com.normal.core.lock.CallBack;
import com.normal.core.lock.DisReentrantLock;
import com.normal.core.lock.DisReentrantLockExecutorTemplate;
import com.normal.core.lock.RedisReentrantLock;
import com.normal.trysth.objs.Student;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author hefei
 * @date 2017/7/25
 * "ERR"异常，refer to https://stackoverflow.com/questions/34200718/redis-troughs-err-operation-not-permitted-error-even-after-properly-running-fo
 * 可以服务到业务场景的：
 * 1.阅读量，点击量
 * 2.不同缓存数据的数据类型选择
 * 3.分布式锁
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {CacheConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RedisTest {
    private static Logger logger = LoggerFactory.getLogger(RedisTest.class);
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testSerializer() {
        ValueOperations valueOps = redisTemplate.opsForValue();
        valueOps.set("key", new Student("this is name 中文"));
        Student obj = (Student) valueOps.get("key");
    }

    //OpsForValue常用命令 append, incr(用于计数场景), setnx(分布式锁)
    @Test
    public void testValue() {
        Object value = redisTemplate.execute((RedisCallback) connection -> connection.decr("key".getBytes()));
        Boolean ifAbsent = redisTemplate.opsForValue().setIfAbsent("key", "newvalue");
        Assert.assertEquals(false, ifAbsent);
    }

    //redis hash结构大致与hashMap 结构相同，hash内容包含一个hashtable，hashtable是一个数组，
    //数组下标是索引，每个数组项关联一个键值对列表（这点与hashmap不同，hashmap链表中存的是值）
    //redis存的是键值对
    @Test
    public void testHash() {
        HashOperations hasOps = redisTemplate.opsForHash();

    }

    //redis中的set，zset 基于跳跃表实现： refer from http://kenby.iteye.com/blog/1187303
    //支持取交集，并集,集合合并（sunion） 集合中移动，集合中取随机元素，
    @Test
    public void testSet() {
        SetOperations setOps = redisTemplate.opsForSet();
        Student obj1 = new Student("obj1");
        Student obj2 = new Student("obj2");
        Student obj3 = new Student("obj3");
        setOps.add("set1", obj1, obj2);
        setOps.add("set2", obj2, obj3);
        Set inter = setOps.intersect("set1", "set2");
        Object interObj = inter.iterator().next();
        Assert.assertEquals(obj2, interObj);
    }

    //有序集合:redis 基于压缩表和字典实现
    //支持排序，汇总，平均数的计算操作,适用于一些汇总信息的统计需求场景？
    @Test
    public void testZSet() {
        Random random = new Random();
        ZSetOperations zsetOps = redisTemplate.opsForZSet();
        /*for (int i =0 ;i<10 ;i++) {
            int score = random.nextInt();
            Student peo = new Student("name" + i, score);
            zsetOps.add("peopleSet", peo, score);
        }*/
        //最后一个： -1L为最后，第一从0L开始
        Set peopleSet = zsetOps.range("peopleSet", -1L, -1L);
        logger.info("排序结果:{}", peopleSet);
    }

    //列表：基于压缩列表 || linkedList 实现
    //注:push会移动index; pop 会删除元素
    @Test
    public void testList() {
        ListOperations listOps = redisTemplate.opsForList();
    }

    @Test
    public void testTrans() {
        //watch 后 multi,别的客户端做更新操作,原来的事务操作失败
        ValueOperations valueOps = redisTemplate.opsForValue();
    }

    @Test
    public void testLua() {
        Boolean success = redisTemplate.opsForValue().setIfAbsent("dd", "whatever");
    }

    //验证 set if not exist 命令在多线程环境下的安全性
    @Test
    public void testSetIfNotExt() throws InterruptedException {
        int threadSize = 200;
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(threadSize);
        List<Boolean> result = new ArrayList<>();
        for (int i = 0; i < threadSize; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        startLatch.await();
                        System.out.println(this.getName() + "set if absent exe ..");
                        Boolean success = redisTemplate.opsForValue().setIfAbsent("randomKey", getName());
                        result.add(success);
                        endLatch.countDown();
                    } catch (InterruptedException ignore) {

                    }
                }
            }.start();
            System.out.println(i);
        }
        startLatch.countDown();
        endLatch.await();
        int successCount = 0;
        for (Boolean item : result) {
            if (item.equals(Boolean.TRUE)) {
                successCount++;
            }
        }
        Assert.assertTrue(successCount == 1);
    }

    @Test
    public void testDistributedLock() throws InterruptedException {
        int threadSize = 200;
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(threadSize);

        for (int i = 0; i < threadSize; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        startLatch.await();
                        DisReentrantLock lock = new RedisReentrantLock("lockId", redisTemplate);
                        DisReentrantLockExecutorTemplate template =new DisReentrantLockExecutorTemplate(lock);
                        template.execute(1000, new CallBack() {
                            @Override
                            public Object onGetLock() {
                                return null;
                            }

                            @Override
                            public Object onTimeOut() {
                                return null;
                            }
                        });
                        endLatch.countDown();
                    } catch (InterruptedException ignore) {

                    }
                }
            }.start();
        }
        startLatch.countDown();
        endLatch.await();

    }

}
