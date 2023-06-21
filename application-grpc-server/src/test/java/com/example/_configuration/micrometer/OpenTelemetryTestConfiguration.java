package com.example._configuration.micrometer;

import io.opentelemetry.api.OpenTelemetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OpenTelemetryTestConfiguration {
  @Bean
  OpenTelemetry openTelemetry() {
    return OpenTelemetry.noop();
  }
}
