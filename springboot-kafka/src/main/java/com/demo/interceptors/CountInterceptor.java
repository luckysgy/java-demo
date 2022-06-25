package com.demo.interceptors;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * @author shenguangyang
 * @date 2022-01-12 21:11
 */
public class CountInterceptor implements ProducerInterceptor<String, String> {
    private int successCount = 0;
    private int errorCount = 0;

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        return record;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        if (exception == null) {
            successCount++;
            System.out.println("当前成功计数:"+successCount);
        } else {
            errorCount++;
            System.out.println("当前失败计数:"+errorCount);

        }
    }

    /**
     * Springboot 项目一般不会关闭
     */
    @Override
    public void close() {
        // 关闭的时候结算
        System.out.println("发送成功" + successCount);
        System.out.println("发送失败" + errorCount);
    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
