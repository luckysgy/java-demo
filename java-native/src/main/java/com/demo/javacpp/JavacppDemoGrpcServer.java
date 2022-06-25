package com.demo.javacpp;

import cn.hutool.core.io.FileUtil;
import com.demo.sdk.FindDemoRequest;
import com.demo.sdk.FindDemoResponse;
import com.demo.sdk.JavacppDemoServiceGrpc;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacppexample.jni.JavaCppExample;
import org.bytedeco.javacppexample.jni.MediaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author shenguangyang
 * @date 2022-05-15 16:12
 */
@GrpcService
public class JavacppDemoGrpcServer extends JavacppDemoServiceGrpc.JavacppDemoServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(JavacppDemoGrpcServer.class);
    private static final AtomicInteger requestNum = new AtomicInteger(1);
    private static final String IMAGE_PATH_PRE = "/mnt/project/javacpp-native/images";

    /**
     */
    @Override
    public void findDemo(FindDemoRequest request, StreamObserver<FindDemoResponse> responseObserver) {
        String message = request.getMessage();
        String id = String.valueOf(requestNum.addAndGet(1));
        while (true) {
            JavaCppExample javaCppExample = new JavaCppExample("hjw34er2345".getBytes(StandardCharsets.UTF_8), "测试数据", 34);
            long count = 1;
            while (count++ < 2000) {
                MediaData mediaData = javaCppExample.demo10_1();
                BytePointer imagePointer = mediaData.get_data();
                try {
                    if (imagePointer == null) {
                        TimeUnit.MILLISECONDS.sleep(200);
                        continue;
                    }
                    long length = mediaData.get_length();
                    log.info("id: {}, start read image length: {}", id, length);
                    byte[] image = new byte[(int) length];
                    imagePointer.get(image, 0, (int) length);
                    log.info("id: {}, end read image length: {}", id, length);
                    if (count % 100 == 0) {
                        String imagePath = IMAGE_PATH_PRE + "/demo10-" + id + ".jpg";
                        log.info("id: {}, grpc demo10, count: {}, length: {}", id, count, length);
                        FileUtil.writeBytes(image, imagePath);
                    }
                    FindDemoResponse response = FindDemoResponse.newBuilder()
                            .setImage(ByteString.copyFrom(image)).build();
                    responseObserver.onNext(response);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (imagePointer != null) {
                        imagePointer.releaseReference();
                    }
                    mediaData.releaseReference();
                }

            }
            javaCppExample.releaseReference();
        }
        // responseObserver.onCompleted();
    }
}
