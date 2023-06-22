package com.example._configuration.gateway;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.MethodDescriptor;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.instrumentation.grpc.v1_6.GrpcTelemetry;
import org.springframework.stereotype.Component;

@Component
class GRPCTraceClientInterceptor implements ClientInterceptor {

  ClientInterceptor grpcTelemetryInterceptor;

  @Override
  public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
      MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
    return grpcTelemetryInterceptor.interceptCall(method, callOptions, next);
  }

  GRPCTraceClientInterceptor(OpenTelemetry openTelemetry) {
    this.grpcTelemetryInterceptor = GrpcTelemetry.create(openTelemetry).newClientInterceptor();
  }
}
