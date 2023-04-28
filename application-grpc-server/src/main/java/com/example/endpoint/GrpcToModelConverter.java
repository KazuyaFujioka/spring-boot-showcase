package com.example.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.util.JsonFormat;
import org.springframework.stereotype.Component;

@Component
class GrpcToModelConverter {

  ObjectMapper objectMapper;

  <T> T convert(MessageOrBuilder messageOrBuilder, Class<T> converterClass) {
    try {
      String json = JsonFormat.printer().print(messageOrBuilder);
      return objectMapper.readValue(json, converterClass);
    } catch (InvalidProtocolBufferException | JsonProcessingException exception) {
      throw new RuntimeException(exception);
    }
  }

  GrpcToModelConverter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }
}
