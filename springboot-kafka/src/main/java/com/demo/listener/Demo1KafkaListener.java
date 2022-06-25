package com.demo.listener;

import com.alibaba.fastjson.JSON;
import com.concise.component.mq.common.listener.MqListener;
import com.demo.Demo1Message;
import com.demo.MqConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author shenguangyang
 * @date 2022-01-08 20:21
 */
@Component
public class Demo1KafkaListener implements MqListener {
    private static final Logger log = LoggerFactory.getLogger(Demo1KafkaListener.class);
    private final AtomicInteger count = new AtomicInteger(0);

    @KafkaListener(id = MqConstant.DEMO1_CONSUME_GROUP,
            groupId = MqConstant.DEMO1_CONSUME_GROUP,
            topics = MqConstant.DEMO1_TOPIC)
    public void listen(String message) {
        Demo1Message demo1Message = JSON.parseObject(message, Demo1Message.class);

        log.info("input value: {}, count: {}" , demo1Message, count.addAndGet(1));
    }
}
