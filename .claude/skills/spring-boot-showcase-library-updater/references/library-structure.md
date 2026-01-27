# libs.versions.toml 構造とライブラリ説明

このドキュメントは、`libs.versions.toml`の構造と各ライブラリの説明をまとめています。

## ファイル構造

```toml
[versions]
# バージョン変数の定義

[libraries]
# ライブラリの依存関係定義

[bundles]
# ライブラリのグループ定義

[plugins]
# Gradleプラグインの定義
```

## カテゴリ別ライブラリ

### フレームワーク・コア

#### Spring Boot
- **変数**: `spring-boot`
- **更新確認**: https://spring.io/projects/spring-boot
- **制約**: 3.x系のみ、4.x系には更新しない

#### Spring Cloud
- **ライブラリ**: `spring-cloud-dependencies`
- **変数**: `spring-cloud`
- **更新確認**: https://spring.io/projects/spring-cloud
- **依存**: Spring Bootのバージョンに対応

#### Spring Cloud AWS
- **ライブラリ**: `aws-spring-cloud-s3`
- **更新確認**: https://mvnrepository.com/artifact/io.awspring.cloud/spring-cloud-aws-starter-s3

### gRPC関連

#### gRPC
- **変数**: `grpc`
- **ライブラリ**: `grpc-protobuf`, `grpc-stub`, `grpc-netty`, `grpc-protoc-gen`
- **更新確認**: https://mvnrepository.com/artifact/io.grpc
- **依存**: 特定のProtobufバージョンを要求

#### Protobuf
- **変数**: `protobuf`
- **ライブラリ**: `protobuf-protoc`, `protobuf-java-util`
- **更新確認**: https://github.com/protocolbuffers/protobuf/releases
- **依存**: gRPCのバージョンに合わせる

#### Protobuf Gradle Plugin
- **プラグイン**: `protobuf`
- **更新確認**: https://mvnrepository.com/artifact/com.google.protobuf

#### gRPC Spring Boot Starter
- **変数**: `grpc-spring-boot`
- **ライブラリ**: `grpc-server-spring-boot`, `grpc-client-spring-boot`
- **更新確認**: https://mvnrepository.com/artifact/net.devh

### 監視・トレーシング

#### OpenTelemetry
- **ライブラリ**: `opentelemetry-zipkin`, `opentelemetry-grpc`
- **更新確認**: https://mvnrepository.com/artifact/io.opentelemetry.instrumentation/opentelemetry-grpc-1.6
- **注意**: alpha版は破壊的変更の可能性あり

#### Micrometer
- **ライブラリ**: `micrometer-tracing-bridge`, `micrometer-prometheus`
- **更新確認**: Spring Boot依存のため通常は自動更新

### テスト・品質管理

#### Karate
- **ライブラリ**: `karate`
- **更新確認**: https://github.com/karatelabs/karate/releases
- **注意**: groupIdが変更された（com.intuit.karate → io.karatelabs）

#### Cucumber Reporting
- **ライブラリ**: `cucumber-reporting`
- **更新確認**: https://mvnrepository.com/artifact/net.masterthought/cucumber-reporting

#### ArchUnit
- **ライブラリ**: `archunit`
- **更新確認**: https://mvnrepository.com/artifact/com.tngtech.archunit/archunit-junit5

### ドキュメント・ツール

#### SpringDoc OpenAPI
- **ライブラリ**: `springdoc-openapi`
- **プラグイン**: `springdoc-openapi`
- **更新確認**: https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui
- **依存**: Spring Bootのメジャーバージョンに対応（3.x → 2.x系）

#### Protoc Gen Doc
- **ライブラリ**: `protoc-gen-doc`
- **更新確認**: https://mvnrepository.com/artifact/io.github.pseudomuto/protoc-gen-doc

#### JIG Gradle Plugin
- **プラグイン**: `jig`
- **更新確認**: https://plugins.gradle.org/plugin/org.dddjava.jig-gradle-plugin
- **バージョン形式**: YYYY.MM.N（年月日順）

### ビルドツール

#### Spotless
- **プラグイン**: `spotless`
- **更新確認**: https://mvnrepository.com/artifact/com.diffplug.spotless

#### Gradle
- **ファイル**: `gradle/wrapper/gradle-wrapper.properties`
- **更新確認**: https://gradle.org/releases/
- **更新コマンド**: `./gradlew wrapper --gradle-version [VERSION]`

### その他

#### Jakarta Annotation API
- **ライブラリ**: `jakarta-annotation-api`
- **更新確認**: https://mvnrepository.com/artifact/jakarta.annotation/jakarta.annotation-api
- **注意**: javax.annotation-apiからの移行版

## 更新の優先順位

1. **セキュリティ修正が含まれるもの**: 最優先
2. **フレームワーク・コア**: Spring Boot, Spring Cloud
3. **gRPC関連**: 依存関係に注意
4. **テスト・品質管理ツール**: 互換性に注意
5. **ビルドツール**: Gradle, プラグイン
6. **その他のライブラリ**: 低優先度

## グルーピング例

意味のある単位でグルーピング：

- Spring関連: Spring Boot + Spring Cloud
- gRPC関連: gRPC + Protobuf + OpenTelemetry gRPC
- テスト関連: Karate + Cucumber Reporting
- ビルドツール: Gradle + Spotless + JIG