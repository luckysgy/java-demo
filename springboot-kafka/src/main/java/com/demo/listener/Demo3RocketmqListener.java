package com.demo.listener;

import com.concise.component.mq.common.listener.MqListener;
import com.demo.MqConstant;
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
        topic = MqConstant.DEMO3_TOPIC,
        consumerGroup = MqConstant.DEMO3_CONSUME_GROUP)
public class Demo3RocketmqListener implements MqListener, RocketMQListener<String> {
    private static final Logger log = LoggerFactory.getLogger(Demo3RocketmqListener.class);
    @Override
    public void onMessage(String message) {
        log.info(message);

    }
}
