package com.example._configuration.gateway;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import javax.net.ssl.SSLException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ReactorNettyClientRequestFactory;
import org.springframework.web.client.RestClient;
import reactor.netty.http.client.HttpClient;

@Configuration
class RestClientConfiguration {

  RestClient.Builder builder;

  @Bean
  RestClient restClient(GatewayServerProperties gatewayServerProperties) {
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
          .requestFactory(new ReactorNettyClientRequestFactory(httpClient))
          .build();
    } catch (SSLException exception) {
      throw new RuntimeException(exception);
    }
  }

  RestClientConfiguration(RestClient.Builder builder) {
    this.builder = builder;
  }
}
