package com.example.endpoint;

import com.example.ApplicationServer;
import com.intuit.karate.junit5.Karate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(
    classes = {ApplicationServer.class},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CampaignEndpointTest {

  @LocalServerPort private String runningPort;

  @Karate.Test
  Karate campaignTest() {
    return Karate.run().relativeTo(getClass()).systemProperty("karate.port", runningPort);
  }
}
