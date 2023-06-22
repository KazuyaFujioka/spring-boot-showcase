package com.example._configuration.gateway;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GatewayServerConfiguration {

  @Bean
  @ConfigurationProperties("external.gateway-server")
  GatewayServerProperties gatewayServerProperties() {
    return new GatewayServerProperties();
  }
}
