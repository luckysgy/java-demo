package com.demo.nio;

import java.nio.IntBuffer;

/**
 * @author shenguangyang
 * @date 2021-11-30 20:54
 */
public class BasicBuffer {
    public static void main(String[] args) {
        // 举例说明 NIO 中 Buffer 的使用
        // 创建一个 buffer，大小为5，即可以存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        // 项buffer存数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put( i * 2 );
        }

        // 从buffer读取数据
        // 对buffer进行读写切换
        intBuffer.flip();

        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
