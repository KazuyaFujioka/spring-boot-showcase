package com.example.endpoint;

import com.example.domain.policy.ResourceNotFoundException;
import io.grpc.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.grpc.server.exception.GrpcExceptionHandler;
import org.springframework.stereotype.Component;

@Component
class GrpcExceptionAdvice {

  private static final Logger LOG = LoggerFactory.getLogger(GrpcExceptionAdvice.class);

  @Bean
  GrpcExceptionHandler notFound() {
    return throwable -> {
      if (throwable instanceof ResourceNotFoundException exception) {
        LOG.warn(exception.getMessage());
        return Status.NOT_FOUND
            .withDescription(exception.getMessage())
            .withCause(exception)
            .asException();
      }
      return null;
    };
  }

  @Bean
  GrpcExceptionHandler internalServerError() {
    return throwable -> {
      if (throwable instanceof Exception exception) {
        LOG.error(exception.getMessage());
        return Status.INTERNAL
            .withDescription(throwable.getMessage())
            .withCause(throwable)
            .asException();
      }
      return null;
    };
  }
}
