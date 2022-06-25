package com.demo;

import com.concise.component.mq.common.BaseMqMessage;

/**
 * @author shenguangyang
 * @date 2022-01-09 13:09
 */
public class Demo1Message extends BaseMqMessage {
    private String data;


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
