package com.normal.trysth.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by hefei on 2017/6/7.
 */
public class ReadEventHandler {

    public void handle(SocketChannel channel, ByteBuffer byteBuffer) {
        try {
            channel.configureBlocking(false);
            StringBuffer result = new StringBuffer();
            for (; ; ) {
                byteBuffer.clear();
                int readByteNum = channel.read(byteBuffer);
                result.append(byteBuffer.getChar());
                if (readByteNum < 1) {
                    break;
                }
            }
            System.out.println("read from client:" + result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
