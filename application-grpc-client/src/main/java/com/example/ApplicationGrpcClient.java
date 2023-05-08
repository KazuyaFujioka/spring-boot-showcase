package com.example;

import static com.example.endpoint.grpc.protobuf.service.CampaignServiceGrpc.CampaignServiceBlockingStub;

import com.example.endpoint.grpc.protobuf.service.CampaignServiceGrpc;
import com.example.endpoint.grpc.protobuf.type.Campaign;
import com.example.endpoint.grpc.protobuf.type.Campaigns;
import com.example.endpoint.grpc.protobuf.type.Number;
import com.google.protobuf.StringValue;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NegotiationType;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.grpc.netty.shaded.io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationGrpcClient implements ApplicationRunner {

  Logger LOG = LoggerFactory.getLogger(ApplicationGrpcClient.class);

  public static void main(String[] args) {
    SpringApplication.run(ApplicationGrpcClient.class, args);
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {

    ManagedChannel channel =
        NettyChannelBuilder.forAddress("localhost", 8080)
            .useTransportSecurity()
            .sslContext(
                GrpcSslContexts.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE) // FIXME localhost用
                    .build())
            .negotiationType(NegotiationType.TLS)
            .build();

    CampaignServiceBlockingStub stub = CampaignServiceGrpc.newBlockingStub(channel);

    Campaigns campaigns = stub.findOngoingCampaign(com.google.protobuf.Empty.getDefaultInstance());
    LOG.info(campaigns.toString());

    Campaign campaign =
        stub.findCampaign(Number.newBuilder().setValue(StringValue.of("dOY0aXcFv3grfpT")).build());
    LOG.info(campaign.toString());
  }
}
