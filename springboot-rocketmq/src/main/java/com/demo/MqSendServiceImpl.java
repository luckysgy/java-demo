package com.demo;

import com.concise.component.mq.rocketmq.service.RocketMqService;
import com.demo.listener.EmailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author shenguangyang
 * @date 2022-01-01 18:49
 */
@Service
public class MqSendServiceImpl implements MqSendService {
    @Resource
    private RocketMqService rocketMqSendService;

    @Value("${rocketmq.email.topic}")
    private String emailTopic;

    @Override
    public void send(EmailMessage message) {
        rocketMqSendService.send(message, emailTopic);
    }
}
