package com.demo.sdk;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 * <pre>
 * 上传文件
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.6.1)",
    comments = "Source: UploadFile.proto")
public final class UploadFileGrpc {

  private UploadFileGrpc() {}

  public static final String SERVICE_NAME = "demo.UploadFile";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.demo.sdk.UploadRequest,
      com.demo.sdk.UploadResponse> METHOD_UPLOAD =
      io.grpc.MethodDescriptor.<com.demo.sdk.UploadRequest, com.demo.sdk.UploadResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
          .setFullMethodName(generateFullMethodName(
              "demo.UploadFile", "upload"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.demo.sdk.UploadRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.demo.sdk.UploadResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.demo.sdk.UploadRequest,
      com.demo.sdk.UploadResponse> METHOD_UPLOAD_NOT_STREAM =
      io.grpc.MethodDescriptor.<com.demo.sdk.UploadRequest, com.demo.sdk.UploadResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "demo.UploadFile", "uploadNotStream"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.demo.sdk.UploadRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.demo.sdk.UploadResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static UploadFileStub newStub(io.grpc.Channel channel) {
    return new UploadFileStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static UploadFileBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new UploadFileBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static UploadFileFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new UploadFileFutureStub(channel);
  }

  /**
   * <pre>
   * 上传文件
   * </pre>
   */
  public static abstract class UploadFileImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * 以客户端流的形式发送数据 (给服务端发送数据)
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.demo.sdk.UploadRequest> upload(
        io.grpc.stub.StreamObserver<com.demo.sdk.UploadResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(METHOD_UPLOAD, responseObserver);
    }

    /**
     */
    public void uploadNotStream(com.demo.sdk.UploadRequest request,
        io.grpc.stub.StreamObserver<com.demo.sdk.UploadResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UPLOAD_NOT_STREAM, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_UPLOAD,
            asyncClientStreamingCall(
              new MethodHandlers<
                com.demo.sdk.UploadRequest,
                com.demo.sdk.UploadResponse>(
                  this, METHODID_UPLOAD)))
          .addMethod(
            METHOD_UPLOAD_NOT_STREAM,
            asyncUnaryCall(
              new MethodHandlers<
                com.demo.sdk.UploadRequest,
                com.demo.sdk.UploadResponse>(
                  this, METHODID_UPLOAD_NOT_STREAM)))
          .build();
    }
  }

  /**
   * <pre>
   * 上传文件
   * </pre>
   */
  public static final class UploadFileStub extends io.grpc.stub.AbstractStub<UploadFileStub> {
    private UploadFileStub(io.grpc.Channel channel) {
      super(channel);
    }

    private UploadFileStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UploadFileStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new UploadFileStub(channel, callOptions);
    }

    /**
     * <pre>
     * 以客户端流的形式发送数据 (给服务端发送数据)
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.demo.sdk.UploadRequest> upload(
        io.grpc.stub.StreamObserver<com.demo.sdk.UploadResponse> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(METHOD_UPLOAD, getCallOptions()), responseObserver);
    }

    /**
     */
    public void uploadNotStream(com.demo.sdk.UploadRequest request,
        io.grpc.stub.StreamObserver<com.demo.sdk.UploadResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UPLOAD_NOT_STREAM, getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * 上传文件
   * </pre>
   */
  public static final class UploadFileBlockingStub extends io.grpc.stub.AbstractStub<UploadFileBlockingStub> {
    private UploadFileBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private UploadFileBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UploadFileBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new UploadFileBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.demo.sdk.UploadResponse uploadNotStream(com.demo.sdk.UploadRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UPLOAD_NOT_STREAM, getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * 上传文件
   * </pre>
   */
  public static final class UploadFileFutureStub extends io.grpc.stub.AbstractStub<UploadFileFutureStub> {
    private UploadFileFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private UploadFileFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UploadFileFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new UploadFileFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.demo.sdk.UploadResponse> uploadNotStream(
        com.demo.sdk.UploadRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UPLOAD_NOT_STREAM, getCallOptions()), request);
    }
  }

  private static final int METHODID_UPLOAD_NOT_STREAM = 0;
  private static final int METHODID_UPLOAD = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final UploadFileImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(UploadFileImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_UPLOAD_NOT_STREAM:
          serviceImpl.uploadNotStream((com.demo.sdk.UploadRequest) request,
              (io.grpc.stub.StreamObserver<com.demo.sdk.UploadResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_UPLOAD:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.upload(
              (io.grpc.stub.StreamObserver<com.demo.sdk.UploadResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class UploadFileDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.demo.sdk.UploadFileProto.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (UploadFileGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new UploadFileDescriptorSupplier())
              .addMethod(METHOD_UPLOAD)
              .addMethod(METHOD_UPLOAD_NOT_STREAM)
              .build();
        }
      }
    }
    return result;
  }
}
