package com.example.endpoint;

import static com.example.infrastructure.grpc.protobuf.service.CampaignServiceGrpc.CampaignServiceBlockingStub;

import com.example.infrastructure.grpc.protobuf.service.CampaignServiceGrpc;
import com.example.infrastructure.grpc.protobuf.type.Campaign;
import com.example.infrastructure.grpc.protobuf.type.Campaigns;
import com.example.infrastructure.grpc.protobuf.type.Number;
import com.google.protobuf.StringValue;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lognet.springboot.grpc.context.LocalRunningGrpcPort;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CampaignServiceImplTest {

  @LocalRunningGrpcPort int gRPCRunningPort;

  CampaignServiceBlockingStub stub;

  @BeforeEach
  void setup() {
    ManagedChannel channel =
        ManagedChannelBuilder.forAddress("localhost", gRPCRunningPort).usePlaintext().build();
    stub = CampaignServiceGrpc.newBlockingStub(channel);
  }

  @Test
  @DisplayName("現在実施中のキャンペーン一覧を取得できる")
  void findOngoingCampaign() {
    Campaigns campaigns = stub.findOngoingCampaign(com.google.protobuf.Empty.getDefaultInstance());

    int expect = 2;
    Assertions.assertEquals(expect, campaigns.getCampaignsCount());
  }

  @Test
  @DisplayName("管理番号を指定して該当のキャンペーンを取得できる")
  void findCampaign() {
    Campaign campaign =
        stub.findCampaign(Number.newBuilder().setValue(StringValue.of("dOY0aXcFv3grfpT")).build());

    String expect = "お友達紹介キャンペーン";
    Assertions.assertEquals(expect, campaign.getTitle().getValue());
  }

  @Test
  @DisplayName("存在しない管理番号を指定した場合、キャンペーンが存在しないエラーが返却される")
  void notFoundCampaign() {
    StatusRuntimeException exception =
        Assertions.assertThrows(
            StatusRuntimeException.class,
            () -> {
              stub.findCampaign(Number.newBuilder().setValue(StringValue.of("123")).build());
            });

    Status expect = Status.NOT_FOUND;
    Assertions.assertEquals(expect.getCode(), exception.getStatus().getCode());
  }
}
