package com.example.endpoint;

import com.example.domain.policy.ResourceNotFoundException;
import io.grpc.Status;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcAdvice
public class GrpcExceptionAdvice {

  Logger LOG = LoggerFactory.getLogger(GrpcExceptionAdvice.class);

  @GrpcExceptionHandler
  public Status notFound(ResourceNotFoundException exception) {
    LOG.warn(exception.getMessage());
    return Status.NOT_FOUND.withDescription(exception.getMessage()).withCause(exception);
  }

  @GrpcExceptionHandler
  public Status internalServerError(Exception exception) {
    LOG.error(exception.getMessage());
    return Status.INTERNAL.withDescription(exception.getMessage()).withCause(exception);
  }
}
