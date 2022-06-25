package com.demo;

import com.concise.component.mq.common.listener.MqListenerScan;
import com.concise.component.mq.kafka.enable.EnableKafka;
import com.concise.component.mq.rocketmq.enable.EnableRocketmq;
import com.demo.listener.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author shenguangyang
 * @date 2022-01-08 20:11
 */
@EnableKafka
@EnableRocketmq
@MqListenerScan(basePackages = "com.concise.demo.kafka.listener", listener = {
        Demo1KafkaMessageListener.class, Demo2KafkaListener.class,
        Demo1RocketmqListener.class, Demo3MessageModelListener.class, Demo3RocketmqListener.class
})
@SpringBootApplication
public class DemoKafkaApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoKafkaApplication.class, args);
    }
}
