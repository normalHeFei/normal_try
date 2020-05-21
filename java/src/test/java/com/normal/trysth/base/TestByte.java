package com.normal.trysth.base;

import org.junit.Assert;
import org.junit.Test;

public class TestByte {
    @Test
    public void testByte() {

        Assert.assertEquals(4, Integer.BYTES);
        Assert.assertEquals(4, Float.BYTES);
        Assert.assertEquals(8, Long.BYTES);

     }

    @Test
    public void testThreadLocal() {
        ThreadLocal<String> local = ThreadLocal.withInitial(() -> "test");

        local.remove();

        Assert.assertTrue("test".equals(local.get()));
    }
}
