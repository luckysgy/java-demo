package com.demo.event;

import com.concise.component.mq.common.BaseMqMessage;

/**
 * @author shenguangyang
 * @date 2022-01-02 9:45
 */
public class Demo1Message extends BaseMqMessage {
    private String streamId;


    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }
}
