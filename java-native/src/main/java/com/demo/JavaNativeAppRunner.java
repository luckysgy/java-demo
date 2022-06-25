package com.demo;

import com.demo.javacpp.JavacppDirectMemoryReporter;
import io.grpc.netty.shaded.io.netty.util.internal.PlatformDependent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author shenguangyang
 * @date 2022-05-15 16:13
 */
@Component
public class JavaNativeAppRunner implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(JavaNativeAppRunner.class);
    @Override
    public void run(ApplicationArguments args) throws Exception {
        new JavacppDirectMemoryReporter().init();
        log.info("netty maxDirectMemory: {}", PlatformDependent.maxDirectMemory());
//        JavacppService.callback1();
//        JavacppService.callback2();
//        JavacppService.demo10();
//        JavacppService.demo12();
//        JavacppService.demo13();
//        JavacppService.demo13Solve();
//        JavacppService.demo14();
//        JavacppService.demo15();
//        JavacppService.demo17();
//        JavacppService.demo18();

    }
}
