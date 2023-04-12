package com.example;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** エンドポイントが定義通りに実装が行われているかを確認するテスト */
@AnalyzeClasses
class EndpointArchitectureTest {

  @ArchTest
  static final ArchRule プレゼンテーションレイヤーのControllerクラスはパッケージプライベートである =
      classes()
          .that()
          .haveSimpleNameEndingWith("Controller")
          .and()
          .resideInAPackage(LayeredArchitectureTest.endpointPackage)
          .should()
          .bePackagePrivate();

  @ArchTest
  static final ArchRule プレゼンテーションレイヤーのControllerクラスはRestControllerとRequestMappingアノテーションが付与されている =
      classes()
          .that()
          .haveSimpleNameEndingWith("Controller")
          .and()
          .resideInAPackage(LayeredArchitectureTest.endpointPackage)
          .should()
          .beAnnotatedWith(RestController.class)
          .andShould()
          .beAnnotatedWith(RequestMapping.class);
}
