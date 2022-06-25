package com.demo.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * @author shenguangyang
 * @date 2022-01-02 14:49
 */
public class SimpleNettyClientHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = LoggerFactory.getLogger(SimpleNettyClientHandler.class);
    /**
     * 当通道就绪时候就会触发, 将消息写到服务端
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, server", StandardCharsets.UTF_8));
        log.info("clientCtx: {}", ctx);
    }

    /**
     * 当通道有读取事件时候会触发
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        log.info("服务回复的消息: {}, 服务器地址: {}", byteBuf.toString(StandardCharsets.UTF_8), ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel();
    }
}
