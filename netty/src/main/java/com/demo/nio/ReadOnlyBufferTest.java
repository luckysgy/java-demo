package com.demo.nio;

import java.nio.ByteBuffer;

/**
 * @author shenguangyang
 * @date 2021-12-18 12:52
 */
public class ReadOnlyBufferTest {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);

        for (int i = 0; i < 64; i++) {
            buffer.put((byte) i);
        }

        buffer.flip();

        // 得到一个只读buffer
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());

        while (readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }
        // 会抛出java.nio.ReadOnlyBufferException
        // readOnlyBuffer.put((byte) 12);
    }
}
