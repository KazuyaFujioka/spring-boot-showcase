package com.example.endpoint;

import com.example.application.service.CampaignService;
import com.example.domain.model.Campaign;
import com.example.domain.model.Campaigns;
import com.example.infrastructure.grpc.protobuf.service.CampaignServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GRpcService
class CampaignServiceImpl extends CampaignServiceGrpc.CampaignServiceImplBase {

  Logger LOG = LoggerFactory.getLogger(CampaignServiceImpl.class);

  CampaignService campaignService;

  CampaignModelConverter campaignModelConverter;
  CampaignGrpcConverter campaignGrpcConverter;

  @Override
  public void findOngoingCampaign(
      com.google.protobuf.Empty request,
      StreamObserver<com.example.infrastructure.grpc.protobuf.type.Campaigns> responseObserver) {
    LOG.debug("find ongoing campaign");

    Campaigns campaigns = campaignService.findOngoingCampaigns();

    com.example.infrastructure.grpc.protobuf.type.Campaigns gRPCCampaigns =
        campaignGrpcConverter.convertGrpcCampaigns(campaigns);

    responseObserver.onNext(gRPCCampaigns);
    responseObserver.onCompleted();
  }

  @Override
  public void findCampaign(
      com.example.infrastructure.grpc.protobuf.type.Number request,
      StreamObserver<com.example.infrastructure.grpc.protobuf.type.Campaign> responseObserver) {
    LOG.debug("find campaign");

    com.example.domain.model.Number number = campaignModelConverter.convertToModelNumber(request);
    Campaign campaign = campaignService.findCampaign(number);

    com.example.infrastructure.grpc.protobuf.type.Campaign gRPCCampaign =
        campaignGrpcConverter.convertGrpcCampaign(campaign);

    responseObserver.onNext(gRPCCampaign);
    responseObserver.onCompleted();
  }

  CampaignServiceImpl(
      CampaignService campaignService,
      CampaignModelConverter campaignModelConverter,
      CampaignGrpcConverter campaignGrpcConverter) {
    this.campaignService = campaignService;
    this.campaignModelConverter = campaignModelConverter;
    this.campaignGrpcConverter = campaignGrpcConverter;
  }
}
