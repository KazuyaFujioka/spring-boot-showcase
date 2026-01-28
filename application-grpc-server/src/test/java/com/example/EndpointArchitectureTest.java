package com.example;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.grpc.server.service.GrpcService;

/** エンドポイントが定義通りに実装が行われているかを確認するテスト */
@AnalyzeClasses
class EndpointArchitectureTest {

  @ArchTest
  static final ArchRule プレゼンテーションレイヤーのgRPCサービスクラスはパッケージプライベートである =
      classes()
          .that()
          .haveSimpleNameEndingWith("ServiceImpl")
          .and()
          .resideInAPackage(LayeredArchitectureTest.endpointPackage)
          .should()
          .bePackagePrivate();

  @ArchTest
  static final ArchRule プレゼンテーションレイヤーのgRPCサービスクラスはGRpcServiceアノテーションが付与されている =
      classes()
          .that()
          .haveSimpleNameEndingWith("ServiceImpl")
          .and()
          .resideInAPackage(LayeredArchitectureTest.endpointPackage)
          .should()
          .beAnnotatedWith(GrpcService.class);
}
