package trysth.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author hefei
 * @date 2017/6/6
 */
public class MultiPortEchoClient {
    public void sendData(int[] ports) throws Throwable {
        ByteBuffer byteBuffer = ByteBuffer.wrap("conten".getBytes());
        while (true) {
            for (int i = 0; i < ports.length; i++) {
                SocketChannel socketChannel = SocketChannel.open();
                socketChannel.connect(new InetSocketAddress(ports[i]));
                socketChannel.configureBlocking(false);
                //构造发送数据
                socketChannel.write(byteBuffer);
                byteBuffer.flip();
            }
        }
    }

    public static void main(String[] args) throws Throwable {
        new MultiPortEchoClient().sendData(new int[]{8081});
        /*for (byte b : "bconten".getBytes()) {
            System.out.println(b);
        }
        System.out.println(((char) 98) == 'b');*/

    }
}
