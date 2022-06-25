package com.demo.application;

import cn.hutool.core.util.StrUtil;
import com.demo.sdk.UploadDataRequest;
import com.demo.sdk.UploadFileGrpc;
import com.demo.sdk.UploadRequest;
import com.demo.sdk.UploadResponse;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author shenguangyang
 * @date 2022-01-16 7:54
 */
@Service
public class UploadFileClient {
    private static final Logger log = LoggerFactory.getLogger(UploadFileClient.class);
    /**
     * 异步 stub
     */
    @GrpcClient(value = "uploadFile")
    private UploadFileGrpc.UploadFileStub uploadFileStub;

    @GrpcClient(value = "uploadFile")
    private UploadFileGrpc.UploadFileBlockingStub uploadFileBlockingStub;

    public void uploadNotStream() {
        // 业务代码
        for (int j = 0; j < 1000; j++) {
            List<UploadDataRequest> uploadDataRequest = new ArrayList<>();
            for (int i = 0; i < 150; i++) {
                try (InputStream fileInputStream = new FileInputStream(StrUtil.format("/mnt/images/{}.jpg", i))) {
                    byte[] bytes = IOUtils.toByteArray(fileInputStream);
                    uploadDataRequest.add(UploadDataRequest.newBuilder().setImage(ByteString.copyFrom(bytes)).build());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            long start = System.currentTimeMillis();
            UploadResponse uploadResponse = uploadFileBlockingStub.withDeadlineAfter(60, TimeUnit.SECONDS)
                    .uploadNotStream(UploadRequest.newBuilder().addAllData(uploadDataRequest).build());
            if (!uploadResponse.getSuccess()) {
                log.error("server upload fail");
                continue;
            }
            log.info("client {} upload success, time: {}", j, System.currentTimeMillis() - start);
        }

    }

    /**
     * 客户端流, 这种方式很有可能内存一直在涨
     * @return
     */
    public void upload() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        // 往服务端不断发送数据
        StreamObserver<UploadResponse> responseData = new StreamObserver<UploadResponse>() {
            @Override
            public void onNext(UploadResponse response) {
                // 收到的是客户端发送最后一个数据且服务端处理完成时响应给客户端的数据, 而不是收到每个请求服务端响应结果
                // 服务端只会响应一次, 如果想要获取服务端每次处理的结果, 可以将服务端处理结果放到一个集合中
                // 最后一起返回给客户端
                log.info("客户端流 --- 服务端响应数据: " + response.getSuccess());
            }

            @Override
            public void onError(Throwable t) {
                log.error("客户端流发生错误: {}", t.getMessage());
            }

            @Override
            public void onCompleted() {
                log.info("onCompleted");
                countDownLatch.countDown();
            }
        };
        StreamObserver<UploadRequest> requestData = uploadFileStub.upload(responseData);
        for (int j = 0; j < 1000; j++) {
            List<UploadDataRequest> uploadDataRequest = new ArrayList<>();
            for (int i = 0; i < 150; i++) {

                try (InputStream fileInputStream = new FileInputStream(StrUtil.format("/mnt/images/{}.jpg", i))) {
                    byte[] bytes = IOUtils.toByteArray(fileInputStream);
                    uploadDataRequest.add(UploadDataRequest.newBuilder().setImage(ByteString.copyFrom(bytes)).build());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            long start = System.currentTimeMillis();
            requestData.onNext(UploadRequest.newBuilder().addAllData(uploadDataRequest).build());
            log.info("client {} upload success, time: {}", j, System.currentTimeMillis() - start);
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
