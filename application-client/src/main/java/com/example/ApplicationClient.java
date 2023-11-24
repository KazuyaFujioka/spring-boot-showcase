package com.example;

import com.example.endpoint.grpc.protobuf.service.CampaignServiceGrpc;
import com.example.endpoint.grpc.protobuf.service.CampaignServiceGrpc.CampaignServiceBlockingStub;
import com.example.endpoint.grpc.protobuf.type.Campaign;
import com.example.endpoint.grpc.protobuf.type.Campaigns;
import com.example.endpoint.grpc.protobuf.type.Number;
import com.google.protobuf.StringValue;
import io.grpc.ManagedChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class ApplicationClient implements ApplicationRunner {

  Logger LOG = LoggerFactory.getLogger(ApplicationClient.class);

  public static void main(String[] args) {
    SpringApplication.run(ApplicationClient.class, args);
  }

  RestClient restClient;
  ManagedChannel managedChannel;

  @Override
  public void run(ApplicationArguments args) throws Exception {

    ResponseEntity<String> responseEntity =
        restClient.get().uri("/v1/campaign/ongoing").retrieve().toEntity(String.class);

    String response = responseEntity.getBody();
    LOG.info(response);

    String campaignNumber = "dOY0aXcFv3grfpT";

    responseEntity =
        restClient
            .get()
            .uri("/v1/campaign/{number}", campaignNumber)
            .retrieve()
            .toEntity(String.class);

    response = responseEntity.getBody();
    LOG.info(response);

    CampaignServiceBlockingStub stub = CampaignServiceGrpc.newBlockingStub(managedChannel);

    Campaigns campaigns = stub.findOngoingCampaign(com.google.protobuf.Empty.getDefaultInstance());
    LOG.info(campaigns.toString());

    Campaign campaign =
        stub.findCampaign(Number.newBuilder().setValue(StringValue.of("dOY0aXcFv3grfpT")).build());
    LOG.info(campaign.toString());
  }

  ApplicationClient(RestClient restClient, ManagedChannel managedChannel) {
    this.restClient = restClient;
    this.managedChannel = managedChannel;
  }
}
