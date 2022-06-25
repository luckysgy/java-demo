package com.demo;

import com.concise.component.mq.common.listener.MqListenerScan;
import com.concise.component.mq.rocketmq.enable.EnableRocketmq;
import com.demo.listener.EmailListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author shenguangyang
 * @date 2022-01-01 18:43
 */
@EnableRocketmq
@MqListenerScan(listener = {EmailListener.class}, basePackages = "com.concise.demo.rocketmq.application.event")
@SpringBootApplication
public class DemoRocketmqApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoRocketmqApplication.class, args);
    }
}
