package com.example.endpoint;

import com.example.application.service.CampaignService;
import com.example.domain.model.Campaign;
import com.example.domain.model.Campaigns;
import com.example.endpoint.grpc.protobuf.service.CampaignServiceGrpc;
import com.example.endpoint.grpc.protobuf.type.Number;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GRpcService
class CampaignServiceImpl extends CampaignServiceGrpc.CampaignServiceImplBase {

  Logger LOG = LoggerFactory.getLogger(CampaignServiceImpl.class);

  CampaignService campaignService;

  GrpcToModelConverter grpcToModelConverter;
  ModelToGrpcConverter modelToGrpcConverter;

  @Override
  public void findOngoingCampaign(
      com.google.protobuf.Empty request,
      StreamObserver<com.example.endpoint.grpc.protobuf.type.Campaigns> responseObserver) {
    LOG.debug("find ongoing campaign");

    Campaigns campaigns = campaignService.findOngoingCampaigns();

    com.example.endpoint.grpc.protobuf.type.Campaigns.Builder gRPCCampaignsBuilder =
        com.example.endpoint.grpc.protobuf.type.Campaigns.newBuilder();
    modelToGrpcConverter.convert(campaigns, gRPCCampaignsBuilder);

    responseObserver.onNext(gRPCCampaignsBuilder.build());
    responseObserver.onCompleted();
  }

  @Override
  public void findCampaign(
      Number request,
      StreamObserver<com.example.endpoint.grpc.protobuf.type.Campaign> responseObserver) {
    LOG.debug("find campaign");

    com.example.domain.model.Number number =
        grpcToModelConverter.convert(request, com.example.domain.model.Number.class);
    Campaign campaign = campaignService.findCampaign(number);

    com.example.endpoint.grpc.protobuf.type.Campaign.Builder gRPCCampaignBuilder =
        com.example.endpoint.grpc.protobuf.type.Campaign.newBuilder();
    modelToGrpcConverter.convert(campaign, gRPCCampaignBuilder);

    responseObserver.onNext(gRPCCampaignBuilder.build());
    responseObserver.onCompleted();
  }

  CampaignServiceImpl(
      CampaignService campaignService,
      GrpcToModelConverter grpcToModelConverter,
      ModelToGrpcConverter modelToGrpcConverter) {
    this.campaignService = campaignService;
    this.grpcToModelConverter = grpcToModelConverter;
    this.modelToGrpcConverter = modelToGrpcConverter;
  }
}
