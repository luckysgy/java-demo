package com.demo.mq;

import com.demo.event.Demo1Message;
import com.demo.event.Demo2Message;

/**
 * @author shenguangyang
 * @date 2022-01-02 9:44
 */
public interface MqSendService {
    default void send(Demo1Message demo1Message) throws Exception {}
    default void send(Demo2Message demo2Message) throws Exception {}
}
