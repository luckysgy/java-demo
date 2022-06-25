package com.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 此拦截器用于获取ip，并放入session中
 */
@javax.servlet.annotation.WebFilter(filterName = "sessionFilter",urlPatterns = "/*")
@Order(1)
public class WebFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(WebFilter.class);
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //服务器域名
        String host = request.getHeader("Host");
        //（方式一）来访者公网IP
        String xRealIp = request.getHeader("X-real-ip");
        //（方式二）来访者公网IP
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        //WEB应用IP（127.0.0.1）
        String getRemoteAddr = request.getRemoteAddr();
        log.info("服务器域名: {}, 来访者公网IP(方式1): {}, 来访者公网IP(方式2): {}, getRemoteAddr： {}", host, xRealIp, xForwardedFor, getRemoteAddr);
        request.getSession().setAttribute("ip", request.getRemoteHost());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}