package trysth.jvm;

import org.junit.Test;

/**
 * @author hefei
 * @date 2017/9/21
 * refer from
 * http://www.xwood.net/_site_domain_/_root/5870/5874/t_c255066.html
 * https://github.com/CyC2018/CS-Notes/blob/master/notes/Java%20%E8%99%9A%E6%8B%9F%E6%9C%BA.md#%E5%88%A4%E6%96%AD%E4%B8%80%E4%B8%AA%E5%AF%B9%E8%B1%A1%E6%98%AF%E5%90%A6%E5%8F%AF%E8%A2%AB%E5%9B%9E%E6%94%B6
 * 收集器分类：
 * 1.serial: 年轻代收集器，单线程，简单，会停顿（stop the world）适用于客户端
 * 2.parNew: 年轻代收集器，serial的多线程版本，配合老年代cms使用
 * 3.paraller scavenge:  年轻代收集器，可设定优化目标，吞吐量优先，配合paraller old使用
 * 3.1.吞吐量：执行时间/总时间（gc停顿时间+执行时间）
 * 3.2.主要参数：
 * MaxGCPauseMillis: 最大的停顿毫秒数
 * GCTimeRatio: 吞吐量
 * -XX:+UseAdaptiveSizePolicy: 开启根据设置优化目标自动调整内存分配
 * 新生代为eden+fromSurivor
 * exp:
 * -server -Xmx2g -Xms2g -Xmn256m -XX:PermSize=128m -Xss256k
 * -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled
 * -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m
 * -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70
 */
public class JvmSetInDifferentCase {
    //常用参数：
    //-Xmx/Xms: 最大、小堆内存，一般为物理内存的一般
    //-Xmn: 堆中新生代大小（eden+两个survivor区）
    //-Xss: 线程栈大小
    //--XX:SurvivorRatio=8：默认为8，堆中eden:fromSurvivor:toSurvivor = 8:1:1 => survivorRatio = 9

    //垃圾回收器相关参数：
    //cms(concurrent Mark sweep): 老年代回收器
    //-XX:+UseConcMarkSweepGC: 指定老年代回收器为cms,新生代回收器为parNew(serial收集器的多线程版本)

    //其他参数：
    //    -Xloggc:${目录}/temp_gc.log           （GC日志文件）
    //    -XX:+HeapDumpOnOutOfMemoryError       （内存溢出时生成heapdump文件）
    //    -XX:HeapDumpPath=${目录}              （heapdump文件存放位置）
    //    -XX:+PrintCommandLineFlags -version     find out the GC java used
    private final static int oneMb = 1024 * 1024;


    static public class HowManyMb {
        //1MB
        private byte[] data;

        public HowManyMb(int howMany) {
            this.data = new byte[howMany * 1024 * 1024];
        }
    }

    //-verbose:gc -Xmx20m -Xms20m -Xmn10m -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails
    @Test
    public void observeGcLog() {
        for (; ; ) {
            new HowManyMb(5);
        }
    }
    //-Xmx10m -Xms10m -XX:PermSize=4m -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails
    //trigger full gc cases:
    //  1. dynamic generate lot of class
    //  2.-XX:MaxTenuringThreshold (在年轻代最大的存活年龄阀值),

    @Test
    public void testFullGc() {

    }

}
