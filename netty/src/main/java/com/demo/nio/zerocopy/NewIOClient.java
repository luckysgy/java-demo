package com.demo.nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author shenguangyang
 * @date 2021-12-19 19:03
 */
public class NewIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 6661));

        String fileName = "/mnt/vcr.mp4";
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        //准备发送
        long startTime = System.currentTimeMillis();

        // 在linux下一个transferTo 方法就可以完成传输
        // 在windows 下 一次调用 transferTo 只能发送8m , 就需要分段传输文件
        // transferTo 底层使用到零拷贝

        long fileSize = fileChannel.size();
        // 分片大小
        int spliceSize = 5 * 1024 * 1024;
        // 剩余字节数
        long remaining = fileSize % spliceSize;
        // 分片数
        long sliceNum = (fileSize / spliceSize) + (remaining == 0 ? 0 : 1);

        long transferCount = 0;
        long transferSliceNum = 1;
        while (sliceNum >= transferSliceNum) {
            if (transferSliceNum == sliceNum) {
                transferCount = transferCount + fileChannel.transferTo((transferSliceNum - 1) * spliceSize, remaining, socketChannel);
            } else {
                transferCount = transferCount + fileChannel.transferTo((transferSliceNum - 1) * spliceSize, spliceSize, socketChannel);
            }
            transferSliceNum++;
        }

        System.out.println("发送的总的字节数 =" + transferCount + " 耗时:" + (System.currentTimeMillis() - startTime));

        // 关闭
        fileChannel.close();
    }
}
