package com.example;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

/** モデルが定義通りに実装が行われているかを確認するテスト */
@AnalyzeClasses
class ModelArchitectureTest {

  @ArchTest
  static final ArchRule ドメインレイヤーのモデルパッケージ内のクラスにはデフォルトコンストラクタがある =
      classes()
          .that()
          .areNotEnums()
          .and()
          .areNotInterfaces()
          .and()
          .haveSimpleNameNotEndingWith("Factory")
          .and()
          .haveSimpleNameNotEndingWith("Test")
          .and()
          .resideInAPackage(LayeredArchitectureTest.modelPackage)
          .should(
              new ArchCondition<>("have a default constructor without parameters") {
                @Override
                public void check(JavaClass javaClass, ConditionEvents events) {
                  if (javaClass.isRecord()) return;
                  boolean satisfied =
                      javaClass.getConstructors().stream()
                          .anyMatch(constructor -> constructor.getParameters().isEmpty());
                  String message =
                      javaClass.getDescription()
                          + (satisfied ? " has" : " does not have")
                          + " a default constructor without parameters";
                  events.add(new SimpleConditionEvent(javaClass, satisfied, message));
                }
              });

  @ArchTest
  static final ArchRule ドメインレイヤーのモデルパッケージ内のクラスにはtoStringがオーバーライドされている =
      classes()
          .that()
          .areNotEnums()
          .and()
          .areNotInterfaces()
          .and()
          .haveSimpleNameNotEndingWith("Factory")
          .and()
          .haveSimpleNameNotEndingWith("Test")
          .and()
          .resideInAPackage(LayeredArchitectureTest.modelPackage)
          .should(
              new ArchCondition<>("Override toString") {
                @Override
                public void check(JavaClass javaClass, ConditionEvents events) {
                  boolean satisfied =
                      javaClass.getAllMethods().stream()
                          .filter(method -> method.getDescription().contains("toString"))
                          .anyMatch(
                              method ->
                                  !method
                                      .getDescription()
                                      .equals("Method <java.lang.Object.toString()>"));
                  String message =
                      javaClass.getDescription()
                          + (satisfied ? " has" : " does not have")
                          + " Override toString";
                  events.add(new SimpleConditionEvent(javaClass, satisfied, message));
                }
              });
}
