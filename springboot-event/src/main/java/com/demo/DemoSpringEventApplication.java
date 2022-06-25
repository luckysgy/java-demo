package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 平时开发中会发现Spring提供了一系列的工具满足我们的业务场景，其中有一项是提供了事件的发布和订阅。
 * 事件的发布主要是依靠ApplicationEventPublisher来进行的。
 *
 * @author shenguangyang
 * @date 2022-01-01 17:26
 */
@SpringBootApplication
public class DemoSpringEventApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoSpringEventApplication.class ,args);
    }
}
