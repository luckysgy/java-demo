package com.demo.listener;

import com.concise.component.mq.common.listener.MqListener;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author shenguangyang
 * @date 2022-01-01 18:44
 */
@Component
@RocketMQMessageListener(
        nameServer = "${rocketmq.name-server}",
        topic = "${rocketmq.email.topic}",
        consumerGroup = "${rocketmq.email.consumerGroup}")
public class EmailListener implements MqListener, RocketMQListener<EmailMessage> {
    private static final Logger log = LoggerFactory.getLogger(EmailListener.class);
    @Override
    public void onMessage(EmailMessage emailMessage) {
        log.info(emailMessage.toString());

    }
}
