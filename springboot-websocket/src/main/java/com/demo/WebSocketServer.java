package com.demo;

import com.alibaba.fastjson.JSON;
import com.concise.component.core.utils.StringUtils;
import com.demo.config.WebSocketConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * userId：地址的111就是这个userId"ws://localhost:8181/wsserver/111"
 *
 * @author shenguangyang
 * @date 2021-12-12 19:23
 */
@ServerEndpoint(value = "/wsserver/{userId}", configurator = WebSocketConfigurator.class)
@Component
public class WebSocketServer {
    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * 用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static final ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 接收userId
     */
    private String userId = "";
    /**
     * 流id, 模拟业务中所需要的数据
     */
    private String streamId = "";
    private String ipAddr;

    static {
        new Thread(() -> {
            while (true) {
                try {
                    List<String> onlineIp = new ArrayList<>();
                    for (Map.Entry<String, WebSocketServer> entry : webSocketMap.entrySet()) {
                        Session se = entry.getValue().getSession();
                        if (!se.isOpen()) {
                            log.info("设备 {} 离线了", entry.getKey());
                            onlineIp.add(entry.getKey());
                        }

                    }
                    for (String online : onlineIp) {
                        webSocketMap.remove(online);
                    }
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        log.info("连接对象hashcode: {}", this.hashCode());
        this.session = session;
        this.userId = userId;
        Map<String, Object> userProperties = session.getUserProperties();
        this.ipAddr = (String) userProperties.get(WebSocketConfigurator.IP_ADDR);
        if (webSocketMap.containsKey(this.ipAddr)) {
            webSocketMap.remove(this.ipAddr);
            webSocketMap.put(this.ipAddr, this);
        } else {
            webSocketMap.put(this.ipAddr, this);
            //加入set中
            addOnlineCount();
            //在线数加1
        }

        log.info("用户连接: {} ,当前在线人数为: {}", userId, getOnlineCount());
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.info("用户: {} ,网络异常!!!!!!", userId);
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            //从set中删除
            subOnlineCount();
        }
        log.info("用户 {} 退出, 当前在线人数为 {}", userId, getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {

        log.info("用户消息: {} ,报文: {}, ipAddr: {}", userId, message, ipAddr);

        //可以群发消息, 消息保存到数据库、redis
        if (StringUtils.isNotBlank(message)) {
            try {
                WebSocketRequest webSocketRequest = JSON.parseObject(message, WebSocketRequest.class);
                //追加发送人(防止串改)
                webSocketRequest.setFromUserId(this.userId);
                String toUserId = webSocketRequest.getToUserId();
                //传送给对应toUserId用户的websocket
                if (StringUtils.isNotBlank(toUserId) && webSocketMap.containsKey(toUserId)) {
                    webSocketMap.get(toUserId).sendMessage(JSON.toJSONString(webSocketRequest));
                } else {
                    log.info("请求的userId: {}, 不在该服务器上", toUserId);
                    //否则不在这个服务器上，发送到mysql或者redis
                    WebSocketResponse response = new WebSocketResponse(500, "toUserId 不在该服务器上");
                    this.sendMessage(JSON.toJSONString(response));
                }
            } catch (Exception e) {
                log.error("非json数据");
                WebSocketResponse response = new WebSocketResponse(500, "非json数据");
                this.sendMessage(JSON.toJSONString(response));
            }
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户 {} 错误, 原因是 {}", this.userId, error.getMessage());
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     * 不能直接调用
     */
    public void sendMessage(String message) throws IOException {
        log.info("连接对象hashcode: {}", this.hashCode());
        if (this.session == null) {
            log.error("session为空");
            return;
        }
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 发送自定义消息
     */
    public static void sendInfo(String message, @PathParam("userId") String userId) throws IOException {
        // log.info("发送消息到: {}, 报文: {}", userId, message);
        if (StringUtils.isNotBlank(userId) && webSocketMap.containsKey(userId)) {
            webSocketMap.get(userId).sendMessage(message);
        } else {
            log.warn("用户 {} 不在线!", userId);
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }


    public static void setOnlineCount(int onlineCount) {
        WebSocketServer.onlineCount = onlineCount;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }
}

