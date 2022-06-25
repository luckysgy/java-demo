package com.demo;

import cn.hutool.core.io.FileUtil;
import com.concise.component.core.utils.id.UUIDUtil;
import com.demo.sdk.*;
import com.google.protobuf.ByteString;
import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author shenguangyang
 * @date 2021-09-29 7:20
 */
@GrpcService
public class DeviceGrpcServer extends DemoServiceGrpc.DemoServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(DeviceGrpcServer.class);

    @Override
    public void insertDemo(DemoRequest request, StreamObserver<InsertDemoResponse> responseObserver) {
        try {
            log.info(request.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 返回体构建，入参在request中，这边省略业务代码
            InsertDemoResponse reply = InsertDemoResponse.newBuilder().build();
            // 返回参数
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }

    /**
     * 因为在proto文件中声明为 stream, 因此可以以流的形式不断的向客户端发送数据,
     * 而客户端只需要请求一次就可以不断的收到服务端发来的数据
     *
     * 如果服务端是c++, 请参考 https://github.com/grpc/grpc/blob/fd3bd70939fb4239639fbd26143ec416366e4157/test/cpp/ios/CronetTests/TestHelper.mm
     * <code>
     * Status TestServiceImpl::ResponseStream(ServerContext* context, const EchoRequest* request,
     *                                        grpc::ServerWriter<EchoResponse>* writer) {
     *   EchoResponse response;
     *   int server_responses_to_send =
     *       GetIntValueFromMetadata(kServerResponseStreamsToSend, context->client_metadata(),
     *                               kServerDefaultResponseStreamsToSend);
     *   for (int i = 0; i < server_responses_to_send; i++) {
     *     response.set_message(request->message() + std::to_string(i));
     *     if (i == server_responses_to_send - 1) {
     *       writer->WriteLast(response, grpc::WriteOptions());
     *     } else {
     *       writer->Write(response);
     *     }
     *   }
     *   return Status::OK;
     * }
     * <code/>
     * 关键在于 <code>writer->WriteLast(response, grpc::WriteOptions());<code/> 当发送最后一个数据时候
     * 一定要使用改方法
     * @param request
     * @param responseObserver
     */
    @Override
    public void findDemo(FindDemoRequest request, StreamObserver<FindDemoResponse> responseObserver) {
        ServerCallStreamObserver<FindDemoResponse> streamObserver = (ServerCallStreamObserver<FindDemoResponse>) responseObserver;
        streamObserver.disableAutoInboundFlowControl();
        try {
            log.info(request.getMessage());
            byte[] bytes = FileUtil.readBytes(new File("/mnt/images/demo1.jpg"));
            int count = 10000;
            while (count-- > 0) {
                // 判断流是否已经被取消
                if (streamObserver.isCancelled()) {
                    System.err.println("stream closed or cancelled");
                    break;
                }
//                if (count == 900) {
//                    streamObserver.onError(Status.NOT_FOUND.withDescription("a error").asRuntimeException());
//                }
                // 返回体构建，入参在request中，这边省略业务代码
                FindDemoResponse reply = FindDemoResponse.newBuilder()
                        .setMessage(count + "\t" + UUIDUtil.randomUUID())
                        .setImage(ByteString.copyFrom(bytes))
                        .build();
                // 返回参数
                streamObserver.onNext(reply);
                System.out.println("findDemo: " + count);
                // TimeUnit.MILLISECONDS.sleep(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            streamObserver.onCompleted();
        }
    }

    @Override
    public StreamObserver<UpdateDemoRequest> updateDemo(StreamObserver<UpdateDemoResponse> responseObserver) {
        System.out.println("updateDemo start");
        return new StreamObserver<UpdateDemoRequest>() {
            @Override
            public void onNext(UpdateDemoRequest o) {
                System.out.println("收到客户端发来的数据: " + o.getMessage());
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("客户端流发生错误: {}", throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                log.info("客户端流结束");
                responseObserver.onNext(UpdateDemoResponse.newBuilder().setMessage(true).build());
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<BilateralStreamRequest> bilateralStream(StreamObserver<BilateralStreamResponse> responseObserver) {
        return super.bilateralStream(responseObserver);
    }
}
