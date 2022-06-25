package com.demo.nio.groupchat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author shenguangyang
 * @date 2021-12-19 14:40
 */
public class GroupChatClient {
    private static final Logger log = LoggerFactory.getLogger(GroupChatClient.class);
    // 服务器的ip
    private final static String HOST = "127.0.0.1";
    // 服务器的端口
    private final static int PORT = 6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    public GroupChatClient() {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            // 得到username
            username = socketChannel.getLocalAddress().toString().substring(1);
            log.info("{} is ok", username);
        } catch (IOException e) {
            log.warn(e.getMessage());
        }
    }

    /**
     * 向服务器发送消息
     * @param info
     */
    public void sendInfo(String info) {
        info = username + " 说: " + info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 读取服务器回复的消息
     */
    public void readInfo() {
        try {
            int select = selector.select(1000);
            if (select > 0) { // 有通道发生事件
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isReadable()) {
                        // 得到相关的通道
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        // 得到buffer
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        channel.read(buffer);
                        // 把读到的缓冲区数据转成字符串
                        log.info("{}", new String(buffer.array()).trim());
                    }
                    // 防止重复操作
                    iterator.remove();
                }
            } else {
                // log.info("没有可以用的通道");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        GroupChatClient groupChatClient = new GroupChatClient();
        // 启动一个线程去做这件事
        new Thread(() -> {
            try {
                while (true) {
                    //客户端需要输入信息，创建一个扫描器
                    Scanner scanner = new Scanner(System.in);
                    while (scanner.hasNextLine()) {
                        String msg = scanner.nextLine();
                        groupChatClient.sendInfo(msg);
                    }
                }
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }).start();

        new Thread(() -> {
            try {
                while (true) {
                    groupChatClient.readInfo();
                    TimeUnit.SECONDS.sleep(3);
                }
            } catch (InterruptedException e) {
                log.info(e.getMessage());
            }
        }).start();

        TimeUnit.SECONDS.sleep(60);
    }
}
