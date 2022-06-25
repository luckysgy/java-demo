package com.demo;

import com.concise.component.mq.common.listener.MqListenerScan;

import com.demo.event.RabbitEmailListener;
import com.demo.event.RabbitOrderListener;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author shenguangyang
 * @date 2022-01-01 20:44
 */
@EnableRabbit
@MqListenerScan(listener = {RabbitEmailListener.class, RabbitOrderListener.class},
        basePackages = {"com.concise.demo.rabbitmq.application.event"})
@SpringBootApplication
public class RabbitmqApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabbitmqApplication.class, args);
    }
}
