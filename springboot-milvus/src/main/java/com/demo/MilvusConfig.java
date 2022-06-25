package com.demo;

import io.milvus.client.MilvusServiceClient;
import io.milvus.param.ConnectParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shenguangyang
 * @date 2022/1/19 16:25
 */
@Configuration
public class MilvusConfig {
    @Autowired
    private MilvusProperties milvusProperties;

    @Bean
    public MilvusServiceClient milvusServiceClient() {
        ConnectParam connectParam = ConnectParam.newBuilder()
                .withHost(milvusProperties.getHost())
                .withPort(milvusProperties.getPort())
                .build();
        return new MilvusServiceClient(connectParam);
    }
}
