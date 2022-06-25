package com.demo;

import com.concise.component.mq.common.listener.MqListenerScan;
import com.concise.component.mq.mqtt.enable.EnableMqtt;
import com.demo.event.Demo1Listener;
import com.demo.event.Demo2Listener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author shenguangyang
 * @date 2022-01-02 9:43
 */
@EnableMqtt
@MqListenerScan(basePackages = "com.concise.demo.mqtt.application.event", listener = {Demo1Listener.class, Demo2Listener.class})
@SpringBootApplication
public class MqttApplication {
    public static void main(String[] args) {
        SpringApplication.run(MqttApplication.class, args);
    }
}
