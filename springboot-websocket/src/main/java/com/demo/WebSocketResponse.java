package com.demo;

import java.io.Serializable;

/**
 * @author shenguangyang
 * @date 2021-12-31 8:00
 */

public class WebSocketResponse implements Serializable {
    private Integer code;
    private String message;

    public WebSocketResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public WebSocketResponse() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
