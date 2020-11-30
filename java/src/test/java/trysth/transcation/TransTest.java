package trysth.transcation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 *
 * @author hefei
 * @date 2017/4/21
 * 考虑隔离性的时候，脑子里要有两个事务单元，每个事务单元都有一组读写操作来考虑。
 * 1.原子性：
 * 要么不执行，要么全都执行，每次修改都写入log里面，保留多个修改版本。回滚时不考虑一致性（比如有并发修改操作时，回滚时这个修改操作就会被忽视）问题，直接还原。
 * 2.一致性：
 * 就是加锁，保证数据一致性
 * 3.隔离性：用不同的锁来控制并发访问，隔离性越低（序列化读写>可重复读>读已提交>读未提交），对应的锁对并发访问的限制就越低，造成的副作用（脏读，幻读）就越大。
 *  3.1：序列化读写（排他锁）：强一致性，无论是读读，读写，写读，写写都是串行，性能低
 *  3.2：可重复读: 隔离级别较高，表示同一个事务单元中，两次读的结果都是符合预期的，不会出现脏读和幻读的情况，比如前后两次读当中，又有update，又有insert 并且都提交了，
 * 那么在可重复读的隔离级别下，前后的两次读的内容也是相同的。(可重复读无法避免幻读的情况的产生)
 *       3.2.1: 在mysql中利用gap锁可以避免在可重复读隔离级别下，幻读的产生
 *  3.3：读已提交(意为只有别人已经提交的内容才会被我读到)
 *       a.考虑两个事务单元 a，b ， a开启事务， b开启事务读取a中的数据，a做update操作，a提交，因为”读已提交“ b 再读的话，b前后两次读到的内容也是不一样的.这也叫做不可重复读
 *
 *  3.4：读未提交(写锁) 只有写读操作是被写锁隔离的，其他三种情况都是可以并行的，缺点就是会读到未提交的内容，也叫脏读。
 *  3.5： mvcc(未规范，不同的数据库有不同的实现)
 *      3.5.1： mysql 下innodb存储引擎的实现方式:
 *       SELECT时，读取创建版本号<=当前事务版本号，删除版本号为空或>当前事务版本号。
         INSERT时，保存当前事务版本号为行的创建版本号
         DELETE时，保存当前事务版本号为行的删除版本号
         UPDATE时，插入一条新纪录，保存当前事务版本号为行创建版本号，同时保存当前事务版本号到原来删除的行
 * 幻读和脏读： 幻读，是针对于整张表而言的，表示读->写->读的这组操作中，前后两次的读到的基于全表的汇总信息不一致，如两次读操作之间存在一次insert写操作，那么前后两次的count就不一致了。
 * 脏读，是针对于某一条特定的记录而言的，表示前后两次读，读到的这条记录的信息不一致的问题
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TransTest {

    @Autowired
    private Service1 testService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * 事务方法嵌套调用时，3个主要的传播属性结果测试
     * refer from http://blog.sina.com.cn/s/blog_4b5bc0110100z7jr.html
     */
    @Test
    @Rollback(false)
    public void testPropagation() {

//        Propagation.REQUIRED：
//            当前方法是不是已经再一个事务里了？在的话就不新起事务，不在的话新起事务，无论哪个方法报异常，都可以rollback
        testService.testRequired();


//        Propagation.REQUIRES_NEW：当前方法不管在不在一个事务中，都会新起一个事务
//        如b的传播属性为REQUIRES_NEW话:
//            a调b，a方法出现异常，已执行的b方法不能rollback
//            a调b，a catch b的异常，并吞掉，这样a方法的已执行部分就会被提交
        testService.testRequiredNew();

//        PROPAGATION_NESTED：他的回滚策略是和父事务方法绑定的，如果父事务rollback，他也要回滚
        testService.testNested();
//        事务方法调用同一个类当中的方法，是可以回滚的，被调用的方法和事务方法是处在同一个事务当中的
        testService.testInnerInvokedTransMethod();

    }

    /**
     * 分布式数据库事务为何没有mvcc的实现：获取版本变得不可能，所有的机器都向同一个获取一个时间戳，tps理论上是不可能的。
     */
    @Test
    @Rollback(false)
    public void testXA() {

    }

    @Test
    @Rollback(false)
    public void makeSomeDataForQuery() {
        Date date = new Date();
        for (int i = 0; i < 99999999; i++) {
            jdbcTemplate.update("INSERT INTO people(last_name, first_name, dob, gender) VALUES (?,?,?,?)", "l" + i, "f" + i, date, "m");
        }
    }

    @Test
    @Rollback(false)
    public void testIsolation() {
        //验证 可重复读隔离界别下 幻读是否会产生

    }
}
