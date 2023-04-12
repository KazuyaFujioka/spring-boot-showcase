package com.example;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

class LayeredArchitectureTest {

  static final String basePackage = "com.example";
  static final String modelPackage = basePackage + ".domain.model..";
  static final String domainPackage = basePackage + ".domain..";
  static final String endpointPackage = basePackage + ".endpoint..";
  static final String servicePackage = basePackage + ".application.service..";
  static final String repositoryPackage = basePackage + ".application.repository..";
  static final String infrastructurePackage = basePackage + ".infrastructure..";
  static final String _configurationPackage = basePackage + "._configuration..";

  @ArchTest
  static final ArchRule アプリケーションレイヤーのサービスはプレゼンテーションレイヤーのみから参照されている =
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
          .whereLayer("Service")
          .mayOnlyBeAccessedByLayers("Presentation");

  @ArchTest
  static final ArchRule アプリケーションレイヤーのリポジトリはサービスとインフラストラクチャレイヤーのみから参照されている =
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
          .whereLayer("Repository")
          .mayOnlyBeAccessedByLayers("Service", "Infrastructure");

  @ArchTest
  static final ArchRule インフラストラクチャレイヤーは他のレイヤーから参照されていない =
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
          .whereLayer("Infrastructure")
          .mayNotBeAccessedByAnyLayer();
}
