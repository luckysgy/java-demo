package com.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

/**
 * @author shenguangyang
 * @date 2022-01-02 9:57
 */
@SpringBootTest
class Demo1ServiceTest {
    @Autowired
    private Demo1Service demo1Service;

    @Test
    void test1() throws InterruptedException {
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                demo1Service.test();
            }).start();
        }
        TimeUnit.SECONDS.sleep(60);
    }
}