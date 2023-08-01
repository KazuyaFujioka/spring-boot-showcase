package com.example.endpoint;

import com.example.domain.policy.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class ExceptionAdvice {

  Logger LOG = LoggerFactory.getLogger(ExceptionAdvice.class);

  @ExceptionHandler({ResourceNotFoundException.class})
  ProblemDetail notFound(ResourceNotFoundException exception) {
    LOG.warn(exception.getMessage());
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
    problemDetail.setDetail(exception.getLocalizedMessage());
    return problemDetail;
  }

  @ExceptionHandler({MethodArgumentNotValidException.class})
  ProblemDetail notFound(MethodArgumentNotValidException exception) {
    LOG.warn(exception.getMessage());
    return exception.getBody();
  }

  @ExceptionHandler({Exception.class})
  ProblemDetail internalServerError(Exception exception) {
    LOG.error(exception.getMessage(), exception);
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    problemDetail.setDetail(exception.getLocalizedMessage());
    return problemDetail;
  }
}
