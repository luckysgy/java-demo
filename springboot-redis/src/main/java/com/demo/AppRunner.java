package com.demo;

import com.concise.component.cache.common.service.CacheService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author shenguangyang
 * @date 2022-06-24 5:45
 */
@Component
public class AppRunner implements ApplicationRunner {
    @Resource
    private CacheService cacheService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        cacheService.opsForValue().set("good:001", 100);
    }
}
