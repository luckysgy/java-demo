package com.demo.javacpp;

import cn.hutool.core.util.RandomUtil;
import com.concise.component.log.utils.MDCSpanIdUtils;
import com.concise.component.log.utils.MDCTraceUtils;
import org.bytedeco.javacpp.Pointer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 直接内存监控
 * @author shenguangyang
 * @date 2022-05-15 21:26
 */
public class JavacppDirectMemoryReporter {
    private static final Logger log = LoggerFactory.getLogger(JavacppDirectMemoryReporter.class);

    private static final String BUSINESS_KEY = "javacpp_direct_memory";
    private static String TRACE_ID;
    private static String SPAN_ID;

    public void init() {
        log.info("init");
        TRACE_ID = RandomUtil.randomNumbers(16);
        SPAN_ID = TRACE_ID;
        startReport();
    }

    public void startReport() {
        Runnable runnable = () -> {
            MDCTraceUtils.putTraceId(TRACE_ID);
            MDCSpanIdUtils.putSpanId(SPAN_ID);
            doReport();
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        service.scheduleAtFixedRate(runnable, 30, 3, TimeUnit.SECONDS);
    }

    private void doReport() {
        try {
            long memoryInKB = Pointer.physicalBytes();
            log.info("{}: {} MB", BUSINESS_KEY, memoryInKB / 1024 / 1024);
        } catch (Exception e) {
            log.error("{} error",BUSINESS_KEY, e);
        }
    }
}