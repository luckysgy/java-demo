package com.demo.nio;

import java.nio.ByteBuffer;

/**
 * @author shenguangyang
 * @date 2021-12-18 12:47
 */
public class NIOByteBufferPutGet {
    public static void main(String[] args) {
        ByteBuffer allocate = ByteBuffer.allocate(64);
        allocate.putInt(100);
        allocate.putLong(200L);
        allocate.putChar('a');
        allocate.putShort((short) 4);

        allocate.flip();
        System.out.println(allocate.getInt());
        System.out.println(allocate.getLong());
        System.out.println(allocate.getChar());
        System.out.println(allocate.getShort());
    }
}
