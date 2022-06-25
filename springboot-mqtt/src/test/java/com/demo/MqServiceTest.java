package com.demo;

import com.concise.component.core.utils.id.UUIDUtil;
import com.demo.event.Demo1Message;
import com.demo.event.Demo2Message;
import com.demo.mq.MqSendService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

/**
 * @author shenguangyang
 * @date 2022-01-02 9:48
 */
@SpringBootTest
class MqServiceTest {
    @Autowired
    private MqSendService mqSendService;

    @Test
    void send() throws Exception {
        while (true) {
            Demo1Message message1 = new Demo1Message();
            message1.setStreamId(UUIDUtil.randomUUID());
            mqSendService.send(message1);
            System.out.println("send message1");

            Demo2Message message2 = new Demo2Message();
            message2.setStreamId(UUIDUtil.randomUUID());
            mqSendService.send(message2);
            System.out.println("send message2");
            TimeUnit.SECONDS.sleep(1);
        }
    }
}