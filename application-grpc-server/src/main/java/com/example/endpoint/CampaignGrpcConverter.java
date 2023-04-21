package com.example.endpoint;

import com.example.infrastructure.grpc.protobuf.type.Campaign;
import com.example.infrastructure.grpc.protobuf.type.Campaigns;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import org.springframework.stereotype.Component;

@Component
class CampaignGrpcConverter {

  ObjectMapper objectMapper;

  Campaigns convertGrpcCampaigns(com.example.domain.model.Campaigns campaigns) {
    try {
      String json = objectMapper.writeValueAsString(campaigns);
      com.example.infrastructure.grpc.protobuf.type.Campaigns.Builder campaignsBuilder =
          com.example.infrastructure.grpc.protobuf.type.Campaigns.newBuilder();
      JsonFormat.parser().ignoringUnknownFields().merge(json, campaignsBuilder);
      return campaignsBuilder.build();
    } catch (JsonProcessingException | InvalidProtocolBufferException exception) {
      throw new RuntimeException(exception);
    }
  }

  Campaign convertGrpcCampaign(com.example.domain.model.Campaign campaign) {
    try {
      String json = objectMapper.writeValueAsString(campaign);
      com.example.infrastructure.grpc.protobuf.type.Campaign.Builder campaignBuilder =
          com.example.infrastructure.grpc.protobuf.type.Campaign.newBuilder();
      JsonFormat.parser().ignoringUnknownFields().merge(json, campaignBuilder);
      return campaignBuilder.build();
    } catch (JsonProcessingException | InvalidProtocolBufferException exception) {
      throw new RuntimeException(exception);
    }
  }

  CampaignGrpcConverter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }
}
