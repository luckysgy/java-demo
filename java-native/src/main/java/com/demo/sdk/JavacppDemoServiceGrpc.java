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
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.6.1)",
    comments = "Source: JavacppDemoService.proto")
public final class JavacppDemoServiceGrpc {

  private JavacppDemoServiceGrpc() {}

  public static final String SERVICE_NAME = "demo.JavacppDemoService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.demo.sdk.FindDemoRequest,
      com.demo.sdk.FindDemoResponse> METHOD_FIND_DEMO =
      io.grpc.MethodDescriptor.<com.demo.sdk.FindDemoRequest, com.demo.sdk.FindDemoResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
          .setFullMethodName(generateFullMethodName(
              "demo.JavacppDemoService", "findDemo"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.demo.sdk.FindDemoRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.demo.sdk.FindDemoResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static JavacppDemoServiceStub newStub(io.grpc.Channel channel) {
    return new JavacppDemoServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static JavacppDemoServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new JavacppDemoServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static JavacppDemoServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new JavacppDemoServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class JavacppDemoServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * 以服务端流的形式发送数据 (给客户端发送数据)
     * </pre>
     */
    public void findDemo(com.demo.sdk.FindDemoRequest request,
        io.grpc.stub.StreamObserver<com.demo.sdk.FindDemoResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_FIND_DEMO, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_FIND_DEMO,
            asyncServerStreamingCall(
              new MethodHandlers<
                com.demo.sdk.FindDemoRequest,
                com.demo.sdk.FindDemoResponse>(
                  this, METHODID_FIND_DEMO)))
          .build();
    }
  }

  /**
   */
  public static final class JavacppDemoServiceStub extends io.grpc.stub.AbstractStub<JavacppDemoServiceStub> {
    private JavacppDemoServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private JavacppDemoServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected JavacppDemoServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new JavacppDemoServiceStub(channel, callOptions);
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
  }

  /**
   */
  public static final class JavacppDemoServiceBlockingStub extends io.grpc.stub.AbstractStub<JavacppDemoServiceBlockingStub> {
    private JavacppDemoServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private JavacppDemoServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected JavacppDemoServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new JavacppDemoServiceBlockingStub(channel, callOptions);
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
   */
  public static final class JavacppDemoServiceFutureStub extends io.grpc.stub.AbstractStub<JavacppDemoServiceFutureStub> {
    private JavacppDemoServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private JavacppDemoServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected JavacppDemoServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new JavacppDemoServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_FIND_DEMO = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final JavacppDemoServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(JavacppDemoServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
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
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class JavacppDemoServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.demo.sdk.DemoProto.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (JavacppDemoServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new JavacppDemoServiceDescriptorSupplier())
              .addMethod(METHOD_FIND_DEMO)
              .build();
        }
      }
    }
    return result;
  }
}
