package com.example._configuration.micrometer;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.instrumentation.grpc.v1_6.GrpcTelemetry;
import org.springframework.stereotype.Component;

@Component
public class TraceServerInterceptor implements ServerInterceptor {

  ServerInterceptor grpcTelemetryInterceptor;

  @Override
  public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
      ServerCall<ReqT, RespT> serverCall, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
    return grpcTelemetryInterceptor.interceptCall(serverCall, headers, next);
  }

  TraceServerInterceptor(OpenTelemetry openTelemetry) {
    this.grpcTelemetryInterceptor = GrpcTelemetry.create(openTelemetry).newServerInterceptor();
  }
}
