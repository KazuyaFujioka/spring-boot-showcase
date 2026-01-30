package com.example.endpoint;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.util.JsonFormat;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Component
class GrpcToModelConverter {

  ObjectMapper objectMapper;

  <T> T convert(MessageOrBuilder grpcClass, Class<T> convertClass) {
    try {
      String json = JsonFormat.printer().print(grpcClass);
      return objectMapper.readValue(json, convertClass);
    } catch (InvalidProtocolBufferException | JacksonException exception) {
      throw new RuntimeException(exception);
    }
  }

  GrpcToModelConverter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }
}
