package com.demo.interceptors;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author shenguangyang
 * @date 2022-01-12 21:13
 */
public class TimeInterceptor implements ProducerInterceptor<String,String>  {
    private static final Logger log = LoggerFactory.getLogger(TimeInterceptor.class);
    /**
     * 发送消息之前加上时间戳
     * @param record
     * @return
     */
    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        return new ProducerRecord<>(record.topic(),  record.partition(), record.timestamp(), record.key(),
                "timeMillis: " + System.currentTimeMillis() + ", " + record.value() , record.headers());
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
