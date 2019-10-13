package com.normal.trysth.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

public class TestByteBuf {
    private static final Logger logger = LoggerFactory.getLogger(TestByteBuf.class);

    @Test
    public void tryTest() {

        ByteBuf byteBuf = Unpooled.copiedBuffer("helloWorld", Charset.forName("utf-8"));
        logger.info("readerIndex:{},writeIndex:{}", byteBuf.readerIndex(), byteBuf.writerIndex());

        CharSequence hello = byteBuf.readCharSequence(4, Charset.forName("utf-8"));
        Assert.assertEquals("hell", hello);
        Assert.assertEquals(4,byteBuf.readerIndex());

        byteBuf.clear();
        byteBuf.writerIndex(4);

        byteBuf.readIntLE();
        //每读一个字节readIndex 就后移 一位,并不是代表读的单位数
        Assert.assertEquals(4, byteBuf.readerIndex());


        byteBuf.clear();
        byteBuf.writerIndex(byteBuf.capacity());

        logger.info("readIndex:{},writeIndex:{}", byteBuf.readerIndex(), byteBuf.writerIndex());

    }


    @Test
    public void readEnoughTest() {
        ByteBuf byteBuf = Unpooled.copiedBuffer("helloWorld", Charset.forName("utf-8"));

        ByteBuf inBuf = Unpooled.buffer();

        inBuf.writeBytes(byteBuf, 5);

        Assert.assertEquals(5,inBuf.writerIndex());

        CharSequence hello = inBuf.readCharSequence(5, Charset.forName("utf-8"));

        Assert.assertEquals("hello",hello);


    }




}
