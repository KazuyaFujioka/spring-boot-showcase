package com.example;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Service;

/** サービスが定義通りに実装が行われているかを確認するテスト */
@AnalyzeClasses
class ServiceArchitectureTest {

  @ArchTest
  static final ArchRule アプリケーションレイヤーのServiceクラスはServiceアノテーションが付与されている =
      classes()
          .that()
          .haveSimpleNameEndingWith("Service")
          .and()
          .resideInAPackage(LayeredArchitectureTest.servicePackage)
          .should()
          .beAnnotatedWith(Service.class);
}
