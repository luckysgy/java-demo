package com.demo.javacpp;

import com.demo.sdk.FindDemoRequest;
import com.demo.sdk.FindDemoResponse;
import com.demo.sdk.JavacppDemoServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Iterator;

/**
 * @author shenguangyang
 * @date 2022-05-15 16:20
 */
@Component
public class JavacppDemoGrpcClient {
    private static final Logger log = LoggerFactory.getLogger(JavacppDemoGrpcClient.class);
    /**
     * 异步 stub
     */
    @GrpcClient(value = "javacppDemoService")
    JavacppDemoServiceGrpc.JavacppDemoServiceStub asyncStub;

    /**
     * 阻塞模式
     * 拦截器需要在注解里写明
     */
    @GrpcClient(value = "javacppDemoService")
    JavacppDemoServiceGrpc.JavacppDemoServiceBlockingStub blockingStub;

    public void findDemo() throws InterruptedException {
        FindDemoRequest request = FindDemoRequest.newBuilder().build();
        // 接收服务端不断返回的数据
        Iterator<FindDemoResponse> demo = blockingStub.findDemo(request);
        while (demo.hasNext()) {
//            FindDemoResponse next = demo.next();
            // log.info(demo.next().getMessage());
            // TimeUnit.MILLISECONDS.sleep(10);
        }
        log.info("grpc client end ==> findDemo");
    }
}
