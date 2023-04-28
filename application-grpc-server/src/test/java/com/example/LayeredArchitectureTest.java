package com.example;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

/** 3層レイヤー構造の定義通りに実装が行われているかを確認するテスト */
@AnalyzeClasses(packages = LayeredArchitectureTest.basePackage)
class LayeredArchitectureTest {

  static final String basePackage = "com.example";
  static final String domainPackage = basePackage + ".domain..";
  static final String endpointPackage = basePackage + ".endpoint..";
  static final String servicePackage = basePackage + ".application.service..";
  static final String repositoryPackage = basePackage + ".application.repository..";
  static final String infrastructurePackage = basePackage + ".infrastructure..";
  static final String _configurationPackage = basePackage + "._configuration..";

  @ArchTest
  static final ArchRule プレゼンテーションレイヤーは他のレイヤーから参照されていない =
      layeredArchitecture()
          .consideringAllDependencies()
          .layer("Domain")
          .definedBy(domainPackage)
          .layer("Presentation")
          .definedBy(endpointPackage)
          .layer("Service")
          .definedBy(servicePackage)
          .layer("Repository")
          .definedBy(repositoryPackage)
          .layer("Infrastructure")
          .definedBy(infrastructurePackage)
          .layer("Configuration")
          .definedBy(_configurationPackage)
          .whereLayer("Presentation")
          .mayNotBeAccessedByAnyLayer();
}
