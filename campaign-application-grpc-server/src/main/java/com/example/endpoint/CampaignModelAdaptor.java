package com.example.endpoint;

import com.example.domain.model.Number;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import org.springframework.stereotype.Component;

@Component
class CampaignModelAdaptor {

  ObjectMapper objectMapper;

  Number convertToModelNumber(com.example.infrastructure.grpc.protobuf.type.Number grpcNumber) {
    try {
      String json = JsonFormat.printer().print(grpcNumber);
      NumberRequest request = objectMapper.readValue(json, NumberRequest.class);
      return request.number;
    } catch (InvalidProtocolBufferException | JsonProcessingException exception) {
      throw new RuntimeException(exception);
    }
  }

  private static class NumberRequest {
    @JsonProperty("value")
    Number number;
  }

  CampaignModelAdaptor(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }
}
