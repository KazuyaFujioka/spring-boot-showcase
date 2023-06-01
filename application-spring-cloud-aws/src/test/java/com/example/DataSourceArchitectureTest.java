package com.example;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Repository;

/** データソースが定義通りに実装が行われているかを確認するテスト */
@AnalyzeClasses
class DataSourceArchitectureTest {

  @ArchTest
  static final ArchRule インフラストラクチャレイヤーのDataSourceクラスはパッケージプライベートである =
      classes()
          .that()
          .haveSimpleNameEndingWith("DataSource")
          .and()
          .resideInAPackage(LayeredArchitectureTest.infrastructurePackage)
          .should()
          .bePackagePrivate();

  @ArchTest
  static final ArchRule インフラストラクチャレイヤーのDataSourceクラスはRepositoryアノテーションが付与されている =
      classes()
          .that()
          .haveSimpleNameEndingWith("DataSource")
          .and()
          .resideInAPackage(LayeredArchitectureTest.infrastructurePackage)
          .should()
          .beAnnotatedWith(Repository.class);
}
