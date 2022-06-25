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
 * The device service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.6.1)",
    comments = "Source: DemoService.proto")
public final class DemoServiceGrpc {

  private DemoServiceGrpc() {}

  public static final String SERVICE_NAME = "demo.DemoService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.demo.sdk.DemoRequest,
      com.demo.sdk.InsertDemoResponse> METHOD_INSERT_DEMO =
      io.grpc.MethodDescriptor.<com.demo.sdk.DemoRequest, com.demo.sdk.InsertDemoResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "demo.DemoService", "insertDemo"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.demo.sdk.DemoRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.demo.sdk.InsertDemoResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.demo.sdk.FindDemoRequest,
      com.demo.sdk.FindDemoResponse> METHOD_FIND_DEMO =
      io.grpc.MethodDescriptor.<com.demo.sdk.FindDemoRequest, com.demo.sdk.FindDemoResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
          .setFullMethodName(generateFullMethodName(
              "demo.DemoService", "findDemo"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.demo.sdk.FindDemoRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.demo.sdk.FindDemoResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.demo.sdk.UpdateDemoRequest,
      com.demo.sdk.UpdateDemoResponse> METHOD_UPDATE_DEMO =
      io.grpc.MethodDescriptor.<com.demo.sdk.UpdateDemoRequest, com.demo.sdk.UpdateDemoResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
          .setFullMethodName(generateFullMethodName(
              "demo.DemoService", "updateDemo"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.demo.sdk.UpdateDemoRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.demo.sdk.UpdateDemoResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.demo.sdk.BilateralStreamRequest,
      com.demo.sdk.BilateralStreamResponse> METHOD_BILATERAL_STREAM =
      io.grpc.MethodDescriptor.<com.demo.sdk.BilateralStreamRequest, com.demo.sdk.BilateralStreamResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
          .setFullMethodName(generateFullMethodName(
              "demo.DemoService", "bilateralStream"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.demo.sdk.BilateralStreamRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.demo.sdk.BilateralStreamResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DemoServiceStub newStub(io.grpc.Channel channel) {
    return new DemoServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DemoServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new DemoServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DemoServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new DemoServiceFutureStub(channel);
  }

  /**
   * <pre>
   * The device service definition.
   * </pre>
   */
  public static abstract class DemoServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Sends a message
     * </pre>
     */
    public void insertDemo(com.demo.sdk.DemoRequest request,
        io.grpc.stub.StreamObserver<com.demo.sdk.InsertDemoResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_INSERT_DEMO, responseObserver);
    }

    /**
     * <pre>
     * 以服务端流的形式发送数据 (给客户端发送数据)
     * </pre>
     */
    public void findDemo(com.demo.sdk.FindDemoRequest request,
        io.grpc.stub.StreamObserver<com.demo.sdk.FindDemoResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_FIND_DEMO, responseObserver);
    }

    /**
     * <pre>
     * 以客户端流的形式发送数据 (给服务端发送数据)
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.demo.sdk.UpdateDemoRequest> updateDemo(
        io.grpc.stub.StreamObserver<com.demo.sdk.UpdateDemoResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(METHOD_UPDATE_DEMO, responseObserver);
    }

    /**
     * <pre>
     * 双向流
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.demo.sdk.BilateralStreamRequest> bilateralStream(
        io.grpc.stub.StreamObserver<com.demo.sdk.BilateralStreamResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(METHOD_BILATERAL_STREAM, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_INSERT_DEMO,
            asyncUnaryCall(
              new MethodHandlers<
                com.demo.sdk.DemoRequest,
                com.demo.sdk.InsertDemoResponse>(
                  this, METHODID_INSERT_DEMO)))
          .addMethod(
            METHOD_FIND_DEMO,
            asyncServerStreamingCall(
              new MethodHandlers<
                com.demo.sdk.FindDemoRequest,
                com.demo.sdk.FindDemoResponse>(
                  this, METHODID_FIND_DEMO)))
          .addMethod(
            METHOD_UPDATE_DEMO,
            asyncClientStreamingCall(
              new MethodHandlers<
                com.demo.sdk.UpdateDemoRequest,
                com.demo.sdk.UpdateDemoResponse>(
                  this, METHODID_UPDATE_DEMO)))
          .addMethod(
            METHOD_BILATERAL_STREAM,
            asyncBidiStreamingCall(
              new MethodHandlers<
                com.demo.sdk.BilateralStreamRequest,
                com.demo.sdk.BilateralStreamResponse>(
                  this, METHODID_BILATERAL_STREAM)))
          .build();
    }
  }

  /**
   * <pre>
   * The device service definition.
   * </pre>
   */
  public static final class DemoServiceStub extends io.grpc.stub.AbstractStub<DemoServiceStub> {
    private DemoServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DemoServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DemoServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DemoServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a message
     * </pre>
     */
    public void insertDemo(com.demo.sdk.DemoRequest request,
        io.grpc.stub.StreamObserver<com.demo.sdk.InsertDemoResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_INSERT_DEMO, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 以服务端流的形式发送数据 (给客户端发送数据)
     * </pre>
     */
    public void findDemo(com.demo.sdk.FindDemoRequest request,
        io.grpc.stub.StreamObserver<com.demo.sdk.FindDemoResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_FIND_DEMO, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 以客户端流的形式发送数据 (给服务端发送数据)
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.demo.sdk.UpdateDemoRequest> updateDemo(
        io.grpc.stub.StreamObserver<com.demo.sdk.UpdateDemoResponse> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(METHOD_UPDATE_DEMO, getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * 双向流
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.demo.sdk.BilateralStreamRequest> bilateralStream(
        io.grpc.stub.StreamObserver<com.demo.sdk.BilateralStreamResponse> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(METHOD_BILATERAL_STREAM, getCallOptions()), responseObserver);
    }
  }

  /**
   * <pre>
   * The device service definition.
   * </pre>
   */
  public static final class DemoServiceBlockingStub extends io.grpc.stub.AbstractStub<DemoServiceBlockingStub> {
    private DemoServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DemoServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DemoServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DemoServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a message
     * </pre>
     */
    public com.demo.sdk.InsertDemoResponse insertDemo(com.demo.sdk.DemoRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_INSERT_DEMO, getCallOptions(), request);
    }

    /**
     * <pre>
     * 以服务端流的形式发送数据 (给客户端发送数据)
     * </pre>
     */
    public java.util.Iterator<com.demo.sdk.FindDemoResponse> findDemo(
        com.demo.sdk.FindDemoRequest request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_FIND_DEMO, getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The device service definition.
   * </pre>
   */
  public static final class DemoServiceFutureStub extends io.grpc.stub.AbstractStub<DemoServiceFutureStub> {
    private DemoServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DemoServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DemoServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DemoServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a message
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.demo.sdk.InsertDemoResponse> insertDemo(
        com.demo.sdk.DemoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_INSERT_DEMO, getCallOptions()), request);
    }
  }

  private static final int METHODID_INSERT_DEMO = 0;
  private static final int METHODID_FIND_DEMO = 1;
  private static final int METHODID_UPDATE_DEMO = 2;
  private static final int METHODID_BILATERAL_STREAM = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DemoServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DemoServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_INSERT_DEMO:
          serviceImpl.insertDemo((com.demo.sdk.DemoRequest) request,
              (io.grpc.stub.StreamObserver<com.demo.sdk.InsertDemoResponse>) responseObserver);
          break;
        case METHODID_FIND_DEMO:
          serviceImpl.findDemo((com.demo.sdk.FindDemoRequest) request,
              (io.grpc.stub.StreamObserver<com.demo.sdk.FindDemoResponse>) responseObserver);
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
        case METHODID_UPDATE_DEMO:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.updateDemo(
              (io.grpc.stub.StreamObserver<com.demo.sdk.UpdateDemoResponse>) responseObserver);
        case METHODID_BILATERAL_STREAM:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.bilateralStream(
              (io.grpc.stub.StreamObserver<com.demo.sdk.BilateralStreamResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class DemoServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.demo.sdk.DemoProto.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (DemoServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DemoServiceDescriptorSupplier())
              .addMethod(METHOD_INSERT_DEMO)
              .addMethod(METHOD_FIND_DEMO)
              .addMethod(METHOD_UPDATE_DEMO)
              .addMethod(METHOD_BILATERAL_STREAM)
              .build();
        }
      }
    }
    return result;
  }
}
