package com.example.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import org.springframework.stereotype.Component;

@Component
class ModelToGrpcConverter {

  ObjectMapper objectMapper;

  void convert(Object modelClass, Message.Builder convertClass) {
    try {
      String json = objectMapper.writeValueAsString(modelClass);
      JsonFormat.parser().ignoringUnknownFields().merge(json, convertClass);
    } catch (JsonProcessingException | InvalidProtocolBufferException exception) {
      throw new RuntimeException(exception);
    }
  }

  ModelToGrpcConverter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }
}
