package trysth.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class MultiPortEchoServer {
    private int ports[];
    private ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    public MultiPortEchoServer(int ports[]) throws IOException {
        this.ports = ports;
    }

    private ServerSocketChannel socketChannel;

    public void init() throws IOException {
        Selector selector = Selector.open();
        for (int port : ports) {
            ServerSocketChannel socketChannel = ServerSocketChannel.open();
            socketChannel.bind(new InetSocketAddress(port));
            socketChannel.configureBlocking(false);
            //这里是将channel注册到selector中，而不应该理解为把selector注册到channel中，selector始终只有一个。
            socketChannel.register(selector, SelectionKey.OP_ACCEPT, new AcceptEventHandler());
            this.socketChannel = socketChannel;
            System.out.println("服务端启动，监听在端口：" + port);
        }
        while (true) {
            int selectKeyNum = selector.select();
            if (selectKeyNum < 1) {
                continue;
            }
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                boolean isAcceptOps = (key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT;
                if (isAcceptOps) {
                    AcceptEventHandler handler = (AcceptEventHandler) key.attachment();
                    //更新在这个channel上感兴趣的事件，同一个channel上会有多个事件状态，所以需要重新注册selector?
                    System.out.println("是否是同一个channel:" + key.channel().equals(this.socketChannel));
                    handler.handle((ServerSocketChannel) key.channel(), selector);
                    //important: 处理完一个事件类型的key后，一定得remove，不然死循环。
                    iterator.remove();
                }
                boolean isReadOps = (key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ;
                if (isReadOps) {
                    ReadEventHandler handler = (ReadEventHandler) key.attachment();
                    handler.handle((SocketChannel) key.channel(), byteBuffer);
                    iterator.remove();
                }
                System.out.println("经处理后，本批准备好的 selectonKey 数量还有：" + keys.size());
            }
        }
    }

    public static void main(String[] args) throws Throwable {
        new MultiPortEchoServer(new int[]{8081}).init();
    }
}