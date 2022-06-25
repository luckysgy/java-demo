package com.demo;

import com.concise.component.core.utils.id.UUIDUtil;
import com.demo.sdk.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author shenguangyang
 * @date 2022-01-01 20:05
 */
@Component
public class DemoGrpcClient {
    private static final Logger log = LoggerFactory.getLogger(DemoGrpcClient.class);
    /**
     * 阻塞模式
     * 拦截器需要在注解里写明
     */
    @GrpcClient(value = "demoService", interceptors = HeaderClientInterceptor.class)
    DemoServiceGrpc.DemoServiceBlockingStub demoServiceBlockingStub;

    /**
     * 异步 stub
     */
    @GrpcClient(value = "demoService", interceptors = HeaderClientInterceptor.class)
    DemoServiceGrpc.DemoServiceStub demoServiceStub;

    /**
     * 其中定义好的一个接口
     *
     * @param request
     * @return
     */
    public InsertDemoResponse insertDemo(DemoRequest request) {
        try {
            // 业务代码
            return demoServiceBlockingStub.withDeadlineAfter(3, TimeUnit.SECONDS).insertDemo(request);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 服务端流
     * @param request
     * @return
     */
    public void findDemo(FindDemoRequest request) {
        try {
            // 接收服务端不断返回的数据
            Iterator<FindDemoResponse> Demo = demoServiceBlockingStub.findDemo(request);
            while (Demo.hasNext()) {
                log.info(Demo.next().getMessage());
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 客户端流
     * @return
     */
    public void updateDemo() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        // 往服务端不断发送数据
        StreamObserver<UpdateDemoResponse> responseData = new StreamObserver<UpdateDemoResponse>() {
            @Override
            public void onNext(UpdateDemoResponse response) {
                // 收到的是客户端发送最后一个数据且服务端处理完成时响应给客户端的数据, 而不是收到每个请求服务端响应结果
                // 服务端只会响应一次, 如果想要获取服务端每次处理的结果, 可以将服务端处理结果放到一个集合中
                // 最后一起返回给客户端
                System.out.println("客户端流 --- 服务端响应数据: " + response.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                log.error("客户端流发生错误: {}", t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
                countDownLatch.countDown();
            }
        };
        StreamObserver<UpdateDemoRequest> requestData = demoServiceStub.updateDemo(responseData);
        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
            requestData.onNext(UpdateDemoRequest.newBuilder().setMessage(UUIDUtil.randomUUID()).build());
        }
        // 客户端告诉服务端：数据已经发完了
        requestData.onCompleted();

        try {
            // 开始等待，如果服务端处理完成，那么responseObserver的onCompleted方法会在另一个线程被执行，
            // 那里会执行countDownLatch的countDown方法，一但countDown被执行，下面的await就执行完毕了，
            // await的超时时间设置为2秒
            countDownLatch.await(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("countDownLatch await error", e);
        }
        System.out.println("service finish");
        System.out.println("updateDemo end");
    }
}
