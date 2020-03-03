package com.normal.trysth.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author hf
 * @date 19-8-15
 */
public class TestWebSocketServer {

    public static void main(String[] args) {
         new ServerBootstrap()
                 .channel(NioServerSocketChannel.class);
    }

    static class WebsocketChannelInitializer extends ChannelInitializer{

        @Override
        protected void initChannel(Channel channel) throws Exception {

        }
    }
}
