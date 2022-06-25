package com.demo;

import io.grpc.*;
import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor;

/**
 * 客户端可以实现自定义拦截器，在数据抵达channel之前对数据拦截做一些相关操作，服务端也可以实现相应拦截器，双方做一些数据的交互。
 * @author shenguangyang
 * @date 2021-09-29 7:31
 */
@GrpcGlobalClientInterceptor
public class HeaderClientInterceptor implements ClientInterceptor {
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method,
                                                               CallOptions callOptions,
                                                               Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(
                next.newCall(method, callOptions)) {

            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {

                // 这边可以将自己需要的请求头传入，双方做好约定校验
                super.start(
                        new ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(
                                responseListener) {
                            @Override
                            public void onHeaders(Metadata headers) {
                                super.onHeaders(headers);

                            }
                        }, headers);
            }
        };
    }
}
