package com.demo.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shenguangyang
 * @date 2022-01-02 14:07
 */
public class SimpleNettyServer {
    private static final Logger log = LoggerFactory.getLogger(SimpleNettyServer.class);
    public static void main(String[] args) throws InterruptedException {
        // 创建BossGroup 和 WorkerGroup
        // 说明
        // 1. 创建两个线程组bossGroup 和 workerGroup
        // 2. bossGroup 只是处理连接请求, 真正的和客户端业务处理, 会交给 workerGroup 完成
        // 3. 两个都是无限循环
        // 4. bossGroup 和 workerGroup 含有的子线程 ( NioEventLoop ) 的个数, 默认实际cpu核数 * 2
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 创建服务端的启动对象, 配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)   // 设置两个线程组
                    .channel(NioServerSocketChannel.class) // 使用 NioServerSocketChannel 作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128) // 设置线程队列等待连接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // 设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 给 workerGroup 的 NioEventLoop 对应的管道设置处理器
                        // 向 Pipeline 设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 可以使用集合管理 SocketChannel , 在推送消息时候, 可以将业务加入到 每个 channel 对应的 NioEventLoop 的 taskQueue
                            // 或者 scheduleTaskQueue
                            socketChannel.pipeline().addLast(new SimpleNettyServerHandler());
                        }
                    });
            log.info("服务器已准备...");

            // 绑定一个端口并且同步处理, 生成一个 ChannelFuture 对象
            ChannelFuture channelFuture = bootstrap.bind(6668).sync();

            // 给 channelFuture 注册监听器, 监控我们关心的事件
            channelFuture.addListener(future -> {
                if (future.isSuccess()) {
                    log.info("bind 6668 success！");
                } else {
                    log.error("bind 6668 fail！");
                }
            });

            // 对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
            log.info("server channel clone");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
