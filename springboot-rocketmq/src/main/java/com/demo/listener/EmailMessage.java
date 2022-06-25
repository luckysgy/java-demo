package com.demo.listener;

import com.concise.component.mq.common.BaseMqMessage;

import java.io.Serializable;

/**
 * @author shenguangyang
 * @date 2022-01-01 18:45
 */
public class EmailMessage extends BaseMqMessage implements Serializable {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
