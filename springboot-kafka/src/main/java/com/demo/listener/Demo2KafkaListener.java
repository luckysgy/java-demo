package com.demo.listener;

import com.alibaba.fastjson.JSON;
import com.concise.component.mq.common.listener.MqListener;
import com.demo.Demo2KafkaMessage;
import com.demo.MqConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author shenguangyang
 * @date 2022-01-08 20:21
 */
@Component
public class Demo2KafkaListener implements MqListener {
    private static final Logger log = LoggerFactory.getLogger(Demo2KafkaListener.class);

    @KafkaListener(id = MqConstant.DEMO2_CONSUME_GROUP,
            groupId = MqConstant.DEMO2_CONSUME_GROUP, topics = MqConstant.DEMO2_TOPIC)
    public void listen(String message) {
        Demo2KafkaMessage demo2KafkaMessage = JSON.parseObject(message, Demo2KafkaMessage.class);
        log.info("input value: {}" , demo2KafkaMessage);
    }
}
