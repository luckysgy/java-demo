package com.demo.mq;

import com.concise.component.mq.common.enums.QosEnum;
import com.concise.component.mq.mqtt.service.MqttSendExpandService;
import com.demo.event.Demo1Message;
import com.demo.event.Demo2Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shenguangyang
 * @date 2022-01-02 9:46
 */
@Service
public class MqSendServiceImpl implements MqSendService {
    @Autowired
    private MqttSendExpandService mqttSendExpandService;
    @Override
    public void send(Demo1Message demo1Message) throws Exception {
        mqttSendExpandService.send("test1", QosEnum.QoS1, demo1Message);
    }

    @Override
    public void send(Demo2Message demo2Message) throws Exception {
        mqttSendExpandService.send("test2", QosEnum.QoS1, demo2Message);
    }
}
