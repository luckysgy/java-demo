package com.demo;

import com.demo.listener.EmailMessage;

/**
 * @author shenguangyang
 * @date 2022-01-01 18:48
 */
public interface MqSendService {
    default void send(EmailMessage message) {}
}
