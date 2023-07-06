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
  static final String modelPackage = basePackage + ".domain.model..";
  static final String servicePackage = basePackage + ".application.service..";
  static final String repositoryPackage = basePackage + ".application.repository..";
  static final String infrastructurePackage = basePackage + ".infrastructure..";
  static final String _configurationPackage = basePackage + "._configuration..";

  @ArchTest
  static final ArchRule アプリケーション実行クラスは他のレイヤーから参照されていない =
      layeredArchitecture()
          .consideringAllDependencies()
          .layer("Main")
          .definedBy(basePackage)
          .layer("Domain")
          .definedBy(domainPackage)
          .layer("Service")
          .definedBy(servicePackage)
          .layer("Repository")
          .definedBy(repositoryPackage)
          .layer("Infrastructure")
          .definedBy(infrastructurePackage)
          .layer("Configuration")
          .definedBy(_configurationPackage)
          .whereLayer("Main")
          .mayNotBeAccessedByAnyLayer();

  @ArchTest
  static final ArchRule アプリケーションレイヤーのサービスはアプリケーション実行クラスからのみから参照されている =
      layeredArchitecture()
          .consideringAllDependencies()
          .layer("Main")
          .definedBy(basePackage)
          .layer("Domain")
          .definedBy(domainPackage)
          .layer("Service")
          .definedBy(basePackage + ".application.service..")
          .layer("Repository")
          .definedBy(basePackage + ".application.repository..")
          .layer("Infrastructure")
          .definedBy(basePackage + ".infrastructure..")
          .layer("Configuration")
          .definedBy(_configurationPackage)
          .whereLayer("Service")
          .mayOnlyBeAccessedByLayers("Main");

  @ArchTest
  static final ArchRule アプリケーションレイヤーのリポジトリはサービスとインフラストラクチャレイヤーのみから参照されている =
      layeredArchitecture()
          .consideringAllDependencies()
          .layer("Main")
          .definedBy(basePackage)
          .layer("Domain")
          .definedBy(domainPackage)
          .layer("Service")
          .definedBy(basePackage + ".application.service..")
          .layer("Repository")
          .definedBy(basePackage + ".application.repository..")
          .layer("Infrastructure")
          .definedBy(basePackage + ".infrastructure..")
          .layer("Configuration")
          .definedBy(_configurationPackage)
          .whereLayer("Repository")
          .mayOnlyBeAccessedByLayers("Service", "Infrastructure");

  @ArchTest
  static final ArchRule インフラストラクチャレイヤーは他のレイヤーから参照されていない =
      layeredArchitecture()
          .consideringAllDependencies()
          .layer("Main")
          .definedBy(basePackage)
          .layer("Domain")
          .definedBy(domainPackage)
          .layer("Service")
          .definedBy(basePackage + ".application.service..")
          .layer("Repository")
          .definedBy(basePackage + ".application.repository..")
          .layer("Infrastructure")
          .definedBy(basePackage + ".infrastructure..")
          .layer("Configuration")
          .definedBy(_configurationPackage)
          .whereLayer("Infrastructure")
          .mayNotBeAccessedByAnyLayer();
}
