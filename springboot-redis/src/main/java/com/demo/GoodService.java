package com.demo;

import cn.hutool.core.lang.Console;
import com.concise.component.cache.common.service.CacheService;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author shenguangyang
 * @date 2022-06-23 21:33
 */
@Service
public class GoodService {
    private static final String REDIS_LOCK = "redis_lock";
    @Resource
    private CacheService cacheService;

    @Resource
    private Redisson redisson;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @PostConstruct
    public void init() {

    }
    @SuppressWarnings("unchecked")
    public void buyGoods() {
        RLock lock = redisson.getLock(REDIS_LOCK);
        lock.lock(2, TimeUnit.SECONDS);
        try {
            String obj = stringRedisTemplate.opsForValue().get("good:001");

            int num = obj != null ? Integer.parseInt(obj) : 0;
            if (num > 0) {
                stringRedisTemplate.opsForValue().set("good:001", String.valueOf(num - 1));
                Console.log("当前商品剩余数量: {}", num);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
