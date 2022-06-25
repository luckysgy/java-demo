package com.demo;

import com.concise.component.core.utils.id.UUIDUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author shenguangyang
 * @date 2022-01-08 20:22
 */
@SpringBootTest
class Demo1KafkaMessageListenerTest {
    private static final Logger log = LoggerFactory.getLogger(Demo1KafkaMessageListenerTest.class);
    @Resource
    private KafkaTemplate<Object, Object> template;


    @Test
    void listen() throws Exception {
        Demo1KafkaMessage demo1KafkaMessage = new Demo1KafkaMessage();
        demo1KafkaMessage.setData(UUIDUtil.randomUUID());
        ListenableFuture<SendResult<Object, Object>> result = template.send(MqConstant.DEMO1_TOPIC, demo1KafkaMessage);
        // 同步获取结果
        // SendResult<Object,String> result1 = result.get();

        // 异步获取
        result.addCallback(new ListenableFutureCallback<SendResult<Object, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error(throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<Object, Object> objectObjectSendResult) {
                try {
                    System.out.println(result.get().getRecordMetadata().topic() + "\t" +
                            result.get().getRecordMetadata().partition() + "\t" + result.get().getRecordMetadata().offset());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Demo2KafkaMessage demo2KafkaMessage = new Demo2KafkaMessage();
        demo2KafkaMessage.setData(UUIDUtil.randomUUID());
        template.send(MqConstant.DEMO2_TOPIC, demo2KafkaMessage);

        for (int i = 0; i < 10000; i++) {
            Demo1KafkaMessage message = new Demo1KafkaMessage();
            message.setData(i + "");
            template.send("my_test",message);
        }

        TimeUnit.SECONDS.sleep(30);
    }

    @Test
    public void batchTest() throws Exception {
        for (int i = 0; i < 100000; i++) {
            Demo1KafkaMessage message = new Demo1KafkaMessage();
            message.setData(i + "");
//            Object template = mqService.getTemplate(Demo1KafkaMessage.class);
//            if (template instanceof KafkaTemplate) {
//                KafkaTemplate<Object, Object> objectObjectKafkaTemplate = KafkaHelper.toKafkaTemplate(template);
//                objectObjectKafkaTemplate.send("my_test", JSON.toJSONString(message));
//            }
            template.send(MqConstant.DEMO1_TOPIC, message);
//            kafkaService.send("my_test", JSON.toJSONString(message));
        }

        TimeUnit.SECONDS.sleep(30);
    }
}