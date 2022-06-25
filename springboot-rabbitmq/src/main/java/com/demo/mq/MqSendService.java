package com.demo.mq;

import com.demo.event.RabbitEmailMessage;
import com.demo.event.RabbitOrderMessage;

/**
 * @author shenguangyang
 * @date 2022-01-02 9:05
 */
public interface MqSendService {
    default void send(RabbitEmailMessage message) {}
    default void send(RabbitOrderMessage message) {}
}
