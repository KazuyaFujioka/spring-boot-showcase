package com.example.endpoint;

import com.example.domain.policy.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class ExceptionAdvice {

  Logger LOG = LoggerFactory.getLogger(ExceptionAdvice.class);

  @ExceptionHandler({ResourceNotFoundException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  ResponseEntity<HttpStatus> notFound(Exception exception) {
    LOG.warn(exception.getMessage());
    return new ResponseEntity(HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({Exception.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  ResponseEntity<HttpStatus> internalServerError(Exception exception) {
    LOG.error(exception.getMessage(), exception);
    return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
