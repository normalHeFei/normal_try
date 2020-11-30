package trysth.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author hefei
 * @date 2017/6/7
 */
public class AcceptEventHandler {
    public void handle(ServerSocketChannel channel, Selector selector) {
        try {
            SocketChannel socketChannel = channel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ, new ReadEventHandler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
