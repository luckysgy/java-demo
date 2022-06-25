package com.demo.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author shenguangyang
 * @date 2021-12-18 13:02
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("/mnt/test.txt", "rw");
        // 获取对应通道
        FileChannel channel = randomAccessFile.getChannel();
        /*
            参数1: FileChannel.MapMode.READ_WRITE使用读写模式
            参数2: 0代表起始位置, 可以直接修改
            参数3: 5代表映射到内存的大小(是大小,而不是索引), 即可以将test.txt的多少个字节映射到内存
                可以直接修改的范围是 0-5
         */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(3, (byte) '9');
        randomAccessFile.close();
        System.out.println("修改成功");
    }
}
