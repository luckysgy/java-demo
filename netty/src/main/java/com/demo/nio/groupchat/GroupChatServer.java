package com.demo.nio.groupchat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * @author shenguangyang
 * @date 2021-12-19 13:39
 */
public class GroupChatServer {
    private static final Logger log = LoggerFactory.getLogger(GroupChatServer.class);
    // 定义属性
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    public GroupChatServer() {
        try {
            // 得到选择器
            selector = Selector.open();
            // 得到 ServerSocketChannel
            listenChannel = ServerSocketChannel.open();
            // 绑定端口号
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            // 设置非阻塞
            listenChannel.configureBlocking(false);
            // 注册到selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 监听
     */
    public void listen() {
        while (true) {
            try {
                if (selector.select() == 0) {
                    log.info("等待...");
                    continue;
                }

                // 有事件要处理

                // 遍历得到SelectionKey集合
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    // 监听到accept
                    if (selectionKey.isAcceptable()) {
                        SocketChannel socketChannel = listenChannel.accept();
                        socketChannel.configureBlocking(false);
                        // 注册到 selector 中
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        log.info("{} 上线", socketChannel.getRemoteAddress());
                    }

                    // 监听到channel可读状态
                    if (selectionKey.isReadable()) {
                        // 专门写一个方法
                        readeData(selectionKey);
                    }

                    // 将当前的key进行删除, 防止重复处理
                    iterator.remove();
                }

            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }


    /**
     * 读取客户端消息
     */
    private void readeData(SelectionKey selectionKey) {
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        try {
            // 创建缓存buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int length = channel.read(buffer);
            if (length > 0) {
                // 把缓冲区的数据转成字符串
                String msg = new String(buffer.array()).trim();
                log.info("from client: {}", msg);
                // 向其他客户端转发消息
                sendInfoToOtherClient(msg, channel);
            }
        } catch (IOException e) {
            try {
                log.info("{} 离线了...", channel.getRemoteAddress());
                // 取消注册
                selectionKey.cancel();
                // 关闭通道
                channel.close();
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
        }
    }

    /**
     * 转发消息给其他的客户(对应通道)
     */
    private void sendInfoToOtherClient(String msg, SocketChannel self) throws IOException {
        log.info("服务器转发消息中...");
        // 遍历所有注册到selector上的SocketChannel, 并排除self
        for (SelectionKey key : selector.keys()) {
            Channel targetChannel = key.channel();
            if (targetChannel instanceof SocketChannel && targetChannel != self) {
                SocketChannel dest = (SocketChannel) targetChannel;
                dest.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}
