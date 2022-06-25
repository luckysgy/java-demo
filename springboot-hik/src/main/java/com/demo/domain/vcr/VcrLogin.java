package com.demo.domain.vcr;

import com.concise.component.core.exception.Assert;

import java.io.Serializable;

/**
 * 登录vcr(录像机) 信息
 * @author shenguangyang
 * @date 2021-12-04 8:51
 */
public class VcrLogin implements Serializable {
    /**
     * 设备ip
     */
    private String ip;
    /**
     * 设备端口号
     */
    private int port;
    /**
     * 设备用户名
     */
    private String username;
    /**
     * 设备密码
     */
    private String password;

    public VcrLogin(String ip, int port, String username, String password) {
        Assert.notEmpty(ip, "ip not empty");
        Assert.notEmpty(username, "username not empty");
        Assert.notEmpty(password, "password not empty");
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public VcrLogin() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
