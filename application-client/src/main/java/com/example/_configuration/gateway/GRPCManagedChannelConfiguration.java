package com.example._configuration.gateway;

import io.grpc.ManagedChannel;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;
import javax.net.ssl.SSLException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.internal.InsecureTrustManagerFactory;

@Configuration
class GRPCManagedChannelConfiguration {

  @Bean
  ManagedChannel managedChannel(
      GRPCTraceClientInterceptor grpcTraceClientInterceptor,
      GatewayServerProperties gatewayServerProperties) {
    try {
      return NettyChannelBuilder.forAddress(
              gatewayServerProperties.domain.value(), gatewayServerProperties.port.value())
          .useTransportSecurity()
          .sslContext(
              GrpcSslContexts.forClient()
                  .trustManager(InsecureTrustManagerFactory.INSTANCE) // FIXME localhost用
                  .build())
          .negotiationType(NegotiationType.TLS)
          .intercept(grpcTraceClientInterceptor)
          .build();
    } catch (SSLException exception) {
      throw new RuntimeException(exception);
    }
  }
}
