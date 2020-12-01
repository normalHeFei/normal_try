package trysth.unsafe;

import org.junit.Test;
import sun.misc.Unsafe;

import java.nio.ByteBuffer;

public class TestUnsafe {
    public static void main(String[] args) {
        Unsafe.getUnsafe();
    }


    @Test
    public void testDirectMemoryAlloc() {

        //directByteBuffer

        ByteBuffer bb = ByteBuffer.allocateDirect(1024);


    }

    @Test
    public void testCAS() {

    }
}
