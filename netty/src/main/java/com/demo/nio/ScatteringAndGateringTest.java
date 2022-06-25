package com.demo.nio;

import cn.hutool.core.lang.Console;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Scattering: 将数据写入到buffer时, 可以采用buffer数组， 依次写入 [分散]
 * Gatering: 将数据从buffer中读取数据时, 可以采用buffer数组, 依次读取 [聚合]
 * @author shenguangyang
 * @date 2021-12-18 13:19
 */
public class ScatteringAndGateringTest {
    private static final Logger log = LoggerFactory.getLogger(ScatteringAndGateringTest.class);
    public static void main(String[] args) throws IOException {
        // 使用ServerSocketChannel 和 SocketChannel网络
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        // 绑定端口到socket并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        // 创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[3];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(2);
        byteBuffers[2] = ByteBuffer.allocate(3);

        // 等待客户端连接
        SocketChannel socketChannel = serverSocketChannel.accept();

        // 假定从客户端接收到8个字节
        int messageLenght = 11;
        // 循环读取
        while (true) {
            int byteRead = 0;
            while (byteRead++ < messageLenght) {
                socketChannel.read(byteBuffers);
                Console.log("byteRead: {}", byteRead);
                Arrays.stream(byteBuffers).map(byteBuffer -> "postion: " + byteBuffer.position() + ", limit: " + byteBuffer.limit())
                        .forEach(Console::log);
            }

            // 将所有的buffer进行flip
            Arrays.stream(byteBuffers).forEach(Buffer::flip);

            // 将数据读取显示到客户端
            int byteWrite = 0;
            while (byteWrite++ < messageLenght) {
                socketChannel.write(byteBuffers);
            }

            // 将所有buffer进行clear操作
            Arrays.stream(byteBuffers).forEach(Buffer::clear);
            Console.log("byteRead: {}, byteWrite: {}, messageLenght: {}",byteRead, byteWrite, messageLenght);
        }
    }
}
