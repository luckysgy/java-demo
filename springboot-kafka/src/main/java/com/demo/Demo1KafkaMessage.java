package com.demo;

import com.concise.component.mq.common.BaseMqMessage;

import java.io.Serializable;

/**
 * @author shenguangyang
 * @date 2022-01-09 7:15
 */
public class Demo1KafkaMessage extends BaseMqMessage implements Serializable {
    private String data;


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
