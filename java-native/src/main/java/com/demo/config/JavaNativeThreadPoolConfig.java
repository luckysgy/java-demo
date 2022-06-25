package com.demo.config;

import com.concise.component.core.thread.TtlThreadPoolTaskExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 */
@Configuration
@EnableAsync
public class JavaNativeThreadPoolConfig {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 异步任务执行线程池
     */
    @Bean(name = "myJniThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor myThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new TtlThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(5);
        // 线程队列最大线程数
        executor.setQueueCapacity(1);
        // 线程池中线程最大空闲存活时间
        executor.setKeepAliveSeconds(60);
        // 线程池最大线程数
        executor.setMaxPoolSize(30);
        executor.setThreadNamePrefix("taskExecutor-");
        // 核心线程是否允许超时，默认:false
        executor.setAllowCoreThreadTimeOut(false);
        /*
         * 拒绝策略，默认是AbortPolicy
         * AbortPolicy：丢弃任务并抛出RejectedExecutionException异常
         * DiscardPolicy：丢弃任务但不抛出异常
         * DiscardOldestPolicy：丢弃最旧的处理程序，然后重试，如果执行器关闭，这时丢弃任务
         * CallerRunsPolicy：执行器执行任务失败，则在策略回调方法中执行任务，如果执行器关闭，这时丢弃任务
         */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // IOC容器关闭时是否阻塞等待剩余的任务执行完成，默认:false（true则必须设置setAwaitTerminationSeconds）
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 阻塞IOC容器关闭的时间
        executor.setAwaitTerminationSeconds(60);

        executor.initialize();
        return executor;
    }
}
