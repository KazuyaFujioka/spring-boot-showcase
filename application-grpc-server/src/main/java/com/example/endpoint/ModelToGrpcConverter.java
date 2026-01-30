package com.example.endpoint;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Component
class ModelToGrpcConverter {

  ObjectMapper objectMapper;

  void convert(Object modelClass, Message.Builder convertClass) {
    try {
      String json = objectMapper.writeValueAsString(modelClass);
      JsonFormat.parser().ignoringUnknownFields().merge(json, convertClass);
    } catch (JacksonException | InvalidProtocolBufferException exception) {
      throw new RuntimeException(exception);
    }
  }

  ModelToGrpcConverter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }
}
