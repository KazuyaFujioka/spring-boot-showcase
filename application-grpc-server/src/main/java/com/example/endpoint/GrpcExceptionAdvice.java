package com.example.endpoint;

import com.example.domain.policy.ResourceNotFoundException;
import io.grpc.Status;
import org.lognet.springboot.grpc.recovery.GRpcExceptionHandler;
import org.lognet.springboot.grpc.recovery.GRpcExceptionScope;
import org.lognet.springboot.grpc.recovery.GRpcServiceAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GRpcServiceAdvice
class GrpcExceptionAdvice {

  Logger LOG = LoggerFactory.getLogger(GrpcExceptionAdvice.class);

  @GRpcExceptionHandler
  public Status notFound(ResourceNotFoundException exception, GRpcExceptionScope scope) {
    LOG.warn(exception.getMessage());
    return Status.NOT_FOUND.withDescription(exception.getMessage()).withCause(exception);
  }

  @GRpcExceptionHandler
  public Status internalServerError(Exception exception, GRpcExceptionScope scope) {
    LOG.error(exception.getMessage());
    return Status.INTERNAL.withDescription(exception.getMessage()).withCause(exception);
  }
}
