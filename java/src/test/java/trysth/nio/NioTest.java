package trysth.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author hefei
 * @date 2017/5/26
 * refer :
 * http://ifeve.com/buffers/#flip
 * https://www.ibm.com/developerworks/cn/education/java/j-nio
 * http://blog.csdn.net/historyasamirror/article/details/5778378（关于同步，异步，阻塞，非阻塞，io多路复用）
 */
public class NioTest {
    /**
     * 应用层面：
     * 1.channel相当于 input、output 流的汇总，它是双向的。
     * 2.buffer：数据的容器，channel与buffer交互
     * <p>
     * 这里的读写都是针对于byteBuffer而言的
     * 写模式切换到读模式： flip
     * 读模式切换到写模式： clear,compact
     */
    public static void main(String[] args) throws IOException {
        /*{
            FileInputStream inputStream = new FileInputStream("F:\\ooowin\\normal-all\\normal-stuff\\src\\main\\resources\\application.properties");
            FileOutputStream outputStream = new FileOutputStream("F:\\ooowin\\normal-all\\normal-stuff\\src\\main\\resources\\unknowjavabase.txt");
            FileChannel channel = inputStream.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (true) {
                if (channel.read(buffer) == -1) {
                    break;
                }
            }
            buffer.flip();
            FileChannel outputChannel = outputStream.getChannel();
            outputChannel.write(buffer);
        }*/
        //nio distributeddemo
        {
            ServerSocket server = new ServerSocket();
            server.bind(new InetSocketAddress(8081));
            ServerSocketChannel channel = server.getChannel();
            Selector selector = Selector.open();
            //channel.register(selector,);
        }
    }

    //position: 跟踪已经写了多少数据或读了多少数据，它指向的是下一个字节来自哪个位置，可以理解成一个游标
    //limit:    代表还有多少数据可以取出或还有多少空间可以写入，它的值小于等于capacity
    //capacity: 总的容量
    @Test
    public void testFlip() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        //position: 0, limit: 10, position 为0代表还没有写入数据到buffer
        System.out.printf("position: %d, limit: %d\n", byteBuffer.position(), byteBuffer.limit());
        byteBuffer.flip();
        System.out.printf("position: %d, limit: %d", byteBuffer.position(), byteBuffer.limit());
    }

    @Test
    public void testRewind() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.put(new byte[1]);
        //将读的游标移动到数组头部，用于重新读, position =0
        byteBuffer.rewind();
        System.out.printf("position: %d, limit: %d", byteBuffer.position(), byteBuffer.limit());
    }

    @Test
    public void testClear() {
        //clear 将position至为0，limit为capacity，为重新写作准备，如果数据没有被读完的话，剩下的数据不会被clear
        //与rewind的区别在于前者是为了重新读，而clear是为了重新写？
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        System.out.printf("position: %d, limit: %d", byteBuffer.position(), byteBuffer.limit());
        byteBuffer.put(new byte[1]);
        byteBuffer.put(new byte[1]);
        System.out.printf("position: %d, limit: %d", byteBuffer.position(), byteBuffer.limit());
        //读之前都得flip一下？统一从头部开始读？
        byteBuffer.flip();
        byteBuffer.get();
        System.out.printf("position: %d, limit: %d", byteBuffer.position(), byteBuffer.limit());
        byteBuffer.clear();
        System.out.printf("position: %d, limit: %d", byteBuffer.position(), byteBuffer.limit());
    }

    @Test
    public void testCompact() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.get();
        assertEquals(1, byteBuffer.position());
        //compact 是将没有被读的数据移到起始位置，这时position为未读完数据的长度，这时的limit表示，最多可读到的位置
        byteBuffer.compact();
        System.out.printf("position: %d, limit: %d", byteBuffer.position(), byteBuffer.limit());
    }

    @Test
    public void testScatterAndGatter() {
        //适用于传递消息是由多个部分组成，并且每个部分的大小是固定不变的，这样对于应用而言，处理起来更加方便
        //读多个buffer和写多个buffer 一般都配套使用？
    }

    //selectKey: 可以理解为返回或发送数据的包装类,包含了channel的一些上下文信息，比如interest set,接受到事件的channel
    @Test
    public void testChar() {
        byte[] bytes = "这里是内容".getBytes(Charset.forName("utf-8"));
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        System.out.println(StandardCharsets.UTF_8.decode(buffer).toString());
        buffer.flip();
        System.out.println(StandardCharsets.UTF_8.decode(buffer).toString());
    }


}
