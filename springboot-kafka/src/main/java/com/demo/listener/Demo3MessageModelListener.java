package com.demo.listener;

import com.concise.component.core.exception.BizException;
import com.concise.component.mq.common.listener.MqListener;
import com.demo.MqConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 手动提交offset
 * 需要关闭自动提交 enable-auto-commit: false
 * @author shenguangyang
 * @date 2022-01-09 17:02
 */
@Component
public class Demo3MessageModelListener implements MqListener {
    private static final Logger log = LoggerFactory.getLogger(Demo3MessageModelListener.class);
    private final AtomicInteger count = new AtomicInteger(0);

    /**
     * MANUAL   当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后, 手动调用Acknowledgment.acknowledge()后提交
     */
    @KafkaListener(id = MqConstant.DEMO3_CONSUME_GROUP, groupId = MqConstant.DEMO3_CONSUME_GROUP,
            topics = MqConstant.DEMO3_TOPIC, containerFactory = "manualListenerContainerFactory",
    errorHandler = "myConsumerAwareErrorHandler")
    public void manual(List<String> message, Acknowledgment ack) {
        for (String data : message) {
            log.info("value: {}, count: {}", data, count.addAndGet(1));
            if (count.get() == 5) {
                throw new BizException("发生异常");
            }
        }

        // 直接提交offset
        ack.acknowledge();
    }

//    /**
//     * MANUAL_IMMEDIATE 手动调用Acknowledgment.acknowledge()后立即提交
//     */
//    @KafkaListener(id = MqConstant.DEMO3_CONSUME_GROUP, groupId = MqConstant.DEMO3_CONSUME_GROUP,
//            topics = MqConstant.DEMO3_TOPIC, containerFactory = "manualImmediateListenerContainerFactory")
//    public void manualImmediate(List<String> message, Acknowledgment ack) {
//        for (String data : message) {
//            log.info("value: {}, count: {}", data, count.addAndGet(1));
//        }
//        // 直接提交offset
//        ack.acknowledge();
//    }

//    /**
//     * 当每一条记录被消费者监听器（ListenerConsumer）处理之后提交
//     * 使用RECORD模式的时候，当监听器处理完消息后会自动处理，使用此模式不需要手动消费。
//     */
//    @KafkaListener(id = MqConstant.DEMO3_CONSUME_GROUP, groupId = MqConstant.DEMO3_CONSUME_GROUP,
//            topics = MqConstant.DEMO3_TOPIC, containerFactory = "recordListenerContainerFactory")
//    public void record(List<String> message) {
//        for (String data : message) {
//            log.info("value: {}, count: {}, 处理数据量: {}", data, count.addAndGet(1), message.size());
//        }
//    }

//    /**
//     * TIME 当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后，距离上次提交时间大于TIME时提交
//     */
//    @KafkaListener(id = MqConstant.DEMO3_CONSUME_GROUP, groupId = MqConstant.DEMO3_CONSUME_GROUP,
//            topics = MqConstant.DEMO3_TOPIC, containerFactory = "timeListenerContainerFactory")
//    public void time(List<String> message) {
//        for (String data : message) {
//            log.info("value: {}, count: {}, 处理数据量: {}", data, count.addAndGet(1), message.size());
//        }
//    }

//    /**
//     * TIME 当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后，距离上次提交时间大于TIME时提交
//     */
//    @KafkaListener(id = MqConstant.DEMO3_CONSUME_GROUP, groupId = MqConstant.DEMO3_CONSUME_GROUP,
//            topics = MqConstant.DEMO3_TOPIC, containerFactory = "countListenerContainerFactory")
//    public void count(List<String> message) {
//        for (String data : message) {
//            log.info("value: {}, count: {}, 处理数据量: {}", data, count.addAndGet(1), message.size());
//        }
//    }

}
