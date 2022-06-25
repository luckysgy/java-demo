package com.demo.config;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.Enumeration;
import java.util.Map;

/**
 * 用于将客户端的ip传递给websocket中的session，相当于是一个中介
 */
public class WebSocketConfigurator extends ServerEndpointConfig.Configurator {
 
    public static final String IP_ADDR = "IP.ADDR";

    /**
     * 在SpringBoot启动类上加一个注解@ServletComponentScan：
     * 否则会出现tomcat session获取不到的情况，因为拦截器没有生效。
     */
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
 
        Map<String, Object> attributes = sec.getUserProperties();
        HttpSession session = (HttpSession) request.getHttpSession();
        if (session != null) {
            attributes.put(IP_ADDR, session.getAttribute("ip"));
            Enumeration<String> names = session.getAttributeNames();
            while (names.hasMoreElements()) {
                String name = names.nextElement();
                attributes.put(name, session.getAttribute(name));
            }
        }
    }
}