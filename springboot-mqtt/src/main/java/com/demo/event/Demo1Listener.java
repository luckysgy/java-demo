package com.demo.event;

import com.concise.component.mq.common.enums.QosEnum;
import com.concise.component.mq.common.listener.MqListener;
import com.concise.component.mq.mqtt.listener.IMqttListener;
import com.concise.component.mq.mqtt.listener.MqttMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author shenguangyang
 * @date 2021-12-25 16:48
 */
@Component
@MqttMessageListener(topic = "test1", qos = QosEnum.QoS1)
public class Demo1Listener implements IMqttListener, MqListener {
    private static final Logger log = LoggerFactory.getLogger(Demo1Listener.class);
    @Override
    public void onMessage(String topic, String message) {
        log.info("topic: {}, message: {}", topic, message);
    }
}
