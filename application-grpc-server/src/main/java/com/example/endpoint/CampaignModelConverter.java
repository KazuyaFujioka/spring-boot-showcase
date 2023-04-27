package com.example.endpoint;

import com.example.domain.model.Number;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import org.springframework.stereotype.Component;

@Component
class CampaignModelConverter {

  ObjectMapper objectMapper;

  Number convertToModelNumber(com.example.infrastructure.grpc.protobuf.type.Number grpcNumber) {
    try {
      String json = JsonFormat.printer().print(grpcNumber);
      return objectMapper.readValue(json, Number.class);
    } catch (InvalidProtocolBufferException | JsonProcessingException exception) {
      throw new RuntimeException(exception);
    }
  }

  CampaignModelConverter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }
}
