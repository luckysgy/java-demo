package com.demo.nio;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author shenguangyang
 * @date 2021-10-19 7:41
 */
public class ByteBufferTest {
    private static final Logger log = LoggerFactory.getLogger(ByteBufferTest.class);
    @Test
    public void readFile() {
        //FileChannel
        //获取FileChannel的方式 1、输入输出流 2、RandomAccessFile
        try (FileChannel channel = new FileInputStream("/mnt/data.txt").getChannel()) {
            //准备缓存区
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while (true) {
                //读取通道中的数据，写入到缓存区中
                int i = channel.read(buffer);
                log.debug("读取到的字节数：{}",i);
                if (i == -1){  //没有内容了
                    break;
                }
                //打印数据
                buffer.flip();  //切换到读模式
                while (buffer.hasRemaining()){  //是否还有剩余未读数据
                    byte b = buffer.get();
                    log.debug("实际字节：{}",(char) b);
                }
                buffer.clear();  //切换到写模型
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
