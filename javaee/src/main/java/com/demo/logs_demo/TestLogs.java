package com.demo.logs_demo;

import cn.hutool.core.util.RandomUtil;
import com.concise.component.log.utils.MDCSpanIdUtils;
import com.concise.component.log.utils.MDCTraceUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.TtlMDCAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.TimeUnit;

/**
 * @author shenguangyang
 * @date 2022-05-16 5:49
 */
@SpringBootTest
public class TestLogs {
    private static final Logger log = LoggerFactory.getLogger(TestLogs.class);
    @Autowired
    private ThreadPoolTaskExecutor myThreadPoolTaskExecutor;

//    @Autowired
//    private ThreadPoolTaskExecutor myLogsDemoThreadPoolTaskExecutor;

    /**
     * {@link TtlMDCAdapter}
     * @throws InterruptedException
     */
    @Test
    public void testTraceId() throws InterruptedException {
        String id = RandomUtil.randomNumbers(16);
        MDCTraceUtils.putTraceId(id);
        MDCSpanIdUtils.putSpanId(id);
        log.info("test1");
        // 这里自定义的线程中接收不到traceId和spanId
        new Thread(() -> {
            MDCSpanIdUtils.putSpanId(RandomUtil.randomNumbers(16));
            log.info("test2");
            new Thread(() -> {
                MDCSpanIdUtils.putSpanId(RandomUtil.randomNumbers(16));
                log.info("test3");
            }).start();
        }).start();

        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println();

        // 这里由于被Sleuth封装过, 因此在执行线程之前会自动生成tranId, 我在线程里面将原有的值进行覆盖 (tranId)
        myThreadPoolTaskExecutor.execute(() -> {
            String id1 = RandomUtil.randomNumbers(16);
            MDCTraceUtils.putTraceId(id1);
            MDCSpanIdUtils.putSpanId(id1);
            log.info("test4: {}", id1);
            myThreadPoolTaskExecutor.execute(() -> {
                MDCSpanIdUtils.putSpanId(RandomUtil.randomNumbers(16));
                log.info("test5");
            });
        });

//        TimeUnit.MILLISECONDS.sleep(100);
//        System.out.println();
//        myLogsDemoThreadPoolTaskExecutor.execute(() -> {
//            log.info("test6");
//            myThreadPoolTaskExecutor.execute(() -> {
//                log.info("test7");
//            });
//        });
        TimeUnit.SECONDS.sleep(1);
    }

}
