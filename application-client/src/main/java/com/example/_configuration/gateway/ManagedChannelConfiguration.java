package com.example._configuration.gateway;

import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NegotiationType;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.grpc.netty.shaded.io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import javax.net.ssl.SSLException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ManagedChannelConfiguration {

  @Bean
  ManagedChannel managedChannel(GatewayServerProperties gatewayServerProperties) {
    try {
      // FIXME このChannel生成だとTracingが伝播していかない
      return NettyChannelBuilder.forAddress(
              gatewayServerProperties.domain.value(), gatewayServerProperties.port.value())
          .useTransportSecurity()
          .sslContext(
              GrpcSslContexts.forClient()
                  .trustManager(InsecureTrustManagerFactory.INSTANCE) // FIXME localhost用
                  .build())
          .negotiationType(NegotiationType.TLS)
          .build();
    } catch (SSLException exception) {
      throw new RuntimeException(exception);
    }
  }
}
