package com.example.endpoint;

import com.example.ApplicationServer;
import com.intuit.karate.Results;
import com.intuit.karate.junit5.Karate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(
    classes = {ApplicationServer.class},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EndpointTest {

  @LocalServerPort String runningPort;

  @Test
  void e2eTest() {
    Results results =
        Karate.run().relativeTo(getClass()).systemProperty("karate.port", runningPort).parallel(5);
    Assertions.assertEquals(0, results.getFailCount(), results.getErrorMessages());
  }
}
