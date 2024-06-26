package com.example.endpoint;

import static com.example.endpoint.grpc.protobuf.service.CampaignServiceGrpc.CampaignServiceBlockingStub;

import com.example.endpoint.grpc.protobuf.service.CampaignServiceGrpc;
import com.example.endpoint.grpc.protobuf.type.Campaign;
import com.example.endpoint.grpc.protobuf.type.Campaigns;
import com.example.endpoint.grpc.protobuf.type.Number;
import com.google.protobuf.StringValue;
import io.grpc.ManagedChannel;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NegotiationType;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.grpc.netty.shaded.io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import javax.net.ssl.SSLException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CampaignServiceImplTest {

  CampaignServiceBlockingStub stub;

  @BeforeEach
  void setup() throws SSLException {
    ManagedChannel channel =
        NettyChannelBuilder.forAddress("localhost", 6565)
            .useTransportSecurity()
            .sslContext(
                GrpcSslContexts.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE) // FIXME localhost用
                    .build())
            .negotiationType(NegotiationType.TLS)
            .build();
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
            () -> stub.findCampaign(Number.newBuilder().setValue(StringValue.of("123")).build()));

    Status expect = Status.NOT_FOUND;
    Assertions.assertEquals(expect.getCode(), exception.getStatus().getCode());
  }
}
