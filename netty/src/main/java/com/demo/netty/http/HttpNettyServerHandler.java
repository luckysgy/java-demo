package com.demo.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * 1. SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter 子类
 * 2. HttpObject 表示客户端和服务器端他们相互通讯的数据被封装成 HttpObject
 * @author shenguangyang
 * @date 2022-01-02 18:40
 */
public class HttpNettyServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    private static final Logger log = LoggerFactory.getLogger(HttpNettyServerHandler.class);
    /**
     * 读取客户端数据
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {
        // 判断 msg 是不是 HttpRequest 请求
        if (httpObject instanceof HttpRequest) {
            log.info("httpObject: {}, clientAddress: {}", httpObject.getClass().getName(), channelHandlerContext.channel().remoteAddress());

            // 通过uri过滤指定的资源
            HttpRequest httpRequest = (HttpRequest) httpObject;
            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                log.info("请求了 favicon.ico 资源, 不做任何处理");
                return;
            }

            // 回复信息给浏览器 (htt协议)
            ByteBuf content = Unpooled.copiedBuffer("hello, 我是服务器", StandardCharsets.UTF_8);
            // 构造 http 的响应, 即 httpResponse
            HttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            // 将构建好的 httpResponse 返回
            channelHandlerContext.writeAndFlush(httpResponse);
        }
    }
}
