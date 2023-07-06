package com.example;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

/** リポジトリが定義通りに実装が行われているかを確認するテスト */
@AnalyzeClasses
class RepositoryArchitectureTest {

  @ArchTest
  static final ArchRule アプリケーションレイヤーのRepositoryクラスはインターフェースである =
      classes()
          .that()
          .haveSimpleNameEndingWith("Repository")
          .and()
          .resideInAPackage(LayeredArchitectureTest.repositoryPackage)
          .should()
          .beInterfaces();
}
