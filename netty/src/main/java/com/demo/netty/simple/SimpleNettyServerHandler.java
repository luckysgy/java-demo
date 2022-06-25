package com.demo.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * 1. 我们自定义的 handler 需要继承 netty 规定好的 Handler 适配器
 * 2. 这时我们自定义的handler, 才能称为一个handler
 * @author shenguangyang
 * @date 2022-01-02 14:24
 */
public class SimpleNettyServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = LoggerFactory.getLogger(SimpleNettyServerHandler.class);
    /**
     * 1. 这里我们可以读取客户端的消息
     * 2. ChannelHandlerContext 是上下文对象, 含有管道pipeline(含有很多handler) , 通道 channel (注重数据的读写),
     *      连接的地址
     * 3. Object msg: 就是客户端发送的数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("服务器读取线程: {}", Thread.currentThread().getName());
        // 将msg转成一个ByteBuf, 是 netty 提供的, 而不是 NIO 的 ByteBuffer
        ByteBuf byteBuf = (ByteBuf) msg;
        Channel channel = ctx.channel();
        // 本质双向链表, 涉及到出栈入栈
        ChannelPipeline pipeline = ctx.pipeline();
        log.info("serverCtx: {}", ctx);
        log.info("clientMsg: {}, clientAddress: {}", byteBuf.toString(StandardCharsets.UTF_8), channel.remoteAddress());

        // 必须这里有一个非常耗时的业务中 -> 异步执行 --> 提交到channel 对应的 NioEventLoop 的 taskQueue 中
        // 解决方案1: 用户程序自定义的普通任务
        ctx.channel().eventLoop().execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello, client -- channelRead-1", StandardCharsets.UTF_8));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 解决方案2: 用户自定义定时任务 -> 该任务提交到 scheduleTaskQueue 中
        ctx.channel().eventLoop().schedule(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello, client -- channelRead-2", StandardCharsets.UTF_8));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 10, TimeUnit.SECONDS);
        log.info("go on...");
    }

    /**
     * 数据读取完毕
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 将数据写到缓冲区并刷新到通道中, 相当于 write + flush
        // 对发送的数据需要进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, client -- channelReadComplete", StandardCharsets.UTF_8));
    }

    /**
     * 处理异常, 需要关闭通道
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
