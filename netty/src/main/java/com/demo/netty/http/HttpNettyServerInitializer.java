package com.demo.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author shenguangyang
 * @date 2022-01-02 18:42
 */
public class HttpNettyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 向管道添加处理器
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 加入netty 提供的 HttpServerCodec codec ==> [coder - decoder]
        // HttpServerCodec 说明
        // 1. HttpServerCodec 是 netty 提供的处理 http 的编解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());

        // 加入自定义处理器
        pipeline.addLast("MyHttpNettyServerHandler", new HttpNettyServerHandler());
    }
}
