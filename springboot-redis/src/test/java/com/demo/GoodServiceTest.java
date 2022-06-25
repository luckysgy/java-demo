package com.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author shenguangyang
 * @date 2022-06-23 21:55
 */
@SpringBootTest
class GoodServiceTest {
    @Resource
    private GoodService goodService;

    @Test
    void buyGoods() throws InterruptedException {
        for(int i = 1; i <= 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    goodService.buyGoods();
                }
            }, "t" + i).start();
        }
        TimeUnit.SECONDS.sleep(5);
    }
}