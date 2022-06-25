package com.demo.application;

import com.concise.component.storage.common.service.StorageService;
import com.demo.sdk.UploadDataRequest;
import com.demo.sdk.UploadFileGrpc;
import com.demo.sdk.UploadRequest;
import com.demo.sdk.UploadResponse;
import com.github.yitter.idgen.YitIdHelper;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author shenguangyang
 * @date 2022-01-16 7:48
 */
@GrpcService
public class UploadFileServer extends UploadFileGrpc.UploadFileImplBase {
    private static final Logger log = LoggerFactory.getLogger(UploadFileServer.class);
    @Resource
    private StorageService storageService;
    private static AtomicLong atomicLong = new AtomicLong(0);

    @Override
    public StreamObserver<UploadRequest> upload(StreamObserver<UploadResponse> responseObserver) {
        log.info("upload start");

        return new StreamObserver<UploadRequest>() {
            @Override
            public void onNext(UploadRequest request) {
                try {
                    List<UploadDataRequest> dataList = request.getDataList();
                    for (UploadDataRequest uploadDataRequest : dataList) {
                        long start = System.currentTimeMillis();
                        byte[] decode = uploadDataRequest.getImage().toByteArray();
                        InputStream inputStream = new ByteArrayInputStream(decode);
                        storageService.uploadFile(DemoStorageManage.class, inputStream, "image/jpeg", YitIdHelper.nextId() + ".jpg");
                        log.info("已上传客户端发来的图片数据, time: {}, count: {}", (System.currentTimeMillis() - start), atomicLong.getAndIncrement());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("客户端流发生错误: {}", throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                log.info("客户端流结束");
                responseObserver.onNext(UploadResponse.newBuilder().setSuccess(true).build());
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public void uploadNotStream(UploadRequest request, StreamObserver<UploadResponse> responseObserver) {
        try {
            try {
                List<UploadDataRequest> dataList = request.getDataList();
                for (UploadDataRequest dataRequest : dataList) {
                    long start = System.currentTimeMillis();
                    byte[] decode = dataRequest.toByteArray();
                    InputStream inputStream = new ByteArrayInputStream(decode);
                    storageService.uploadFile(DemoStorageManage.class, inputStream, "image/jpeg", YitIdHelper.nextId() + ".jpg");
                    log.info("已上传客户端发来的图片数据, time: {}, count: {}", (System.currentTimeMillis() - start), atomicLong.getAndIncrement());
                }

            } catch (Exception e) {
                log.error(e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 返回体构建，入参在request中，这边省略业务代码
            UploadResponse reply = UploadResponse.newBuilder().setSuccess(true).build();
            // 返回参数
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }
}
