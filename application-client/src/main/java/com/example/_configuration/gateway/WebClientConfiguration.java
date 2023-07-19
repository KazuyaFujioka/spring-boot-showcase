package com.example._configuration.gateway;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import javax.net.ssl.SSLException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import reactor.netty.http.client.HttpClient;

@Configuration
class WebClientConfiguration {

  Builder builder;

  @Bean
  WebClient webClient(GatewayServerProperties gatewayServerProperties) {
    try {
      SslContext sslContext =
          SslContextBuilder.forClient()
              .trustManager(InsecureTrustManagerFactory.INSTANCE)
              .build(); // FIXME localhost用

      HttpClient httpClient =
          HttpClient.create().compress(true).secure(option -> option.sslContext(sslContext));

      return builder
          .clone()
          .baseUrl(gatewayServerProperties.toUrl())
          .clientConnector(new ReactorClientHttpConnector(httpClient))
          .build();
    } catch (SSLException exception) {
      throw new RuntimeException(exception);
    }
  }

  WebClientConfiguration(Builder builder) {
    this.builder = builder;
  }
}
