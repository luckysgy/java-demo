package com.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author shenguangyang
 * @date 2022-02-04 18:13
 */
@Component
public class InitServer implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(InitServer.class);
    @Value("${test.name:no}")
    private String testName;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("testName: {}", testName);
    }
}
