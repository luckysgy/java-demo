package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author shenguangyang
 * @date 2022/1/19 16:26
 */
@EnableConfigurationProperties(MilvusProperties.class)
@SpringBootApplication
public class MilvusApplication {
    public static void main(String[] args) {
        SpringApplication.run(MilvusApplication.class, args);
    }
}
