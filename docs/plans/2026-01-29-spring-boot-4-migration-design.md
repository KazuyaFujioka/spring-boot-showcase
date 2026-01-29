# Spring Boot 4 移行設計ドキュメント

**日付**: 2026-01-29
**目的**: Spring Boot 3.5.10から4.0.2への移行

## 移行の目的

最新バージョンを維持し、セキュリティパッチを適用することを目的とする。

## 移行アプローチ

**戦略**: 慎重な段階的移行
- 新しいGitブランチで移行を試行
- 各モジュールの動作確認とテスト実施
- 問題があればロールバック可能

**検証方法**: 既存テスト + 手動検証
- 既存のテストスイート（Karate、ArchUnit、Spring Boot Test）を実行
- 各アプリケーションを実際に起動して動作確認

## 依存関係の変更

### 主要バージョン変更

```toml
[versions]
spring-boot = "4.0.2"  # 3.5.10 → 4.0.2
spring-cloud = "2025.1.0"  # 2025.0.1 → 2025.1.0（Oakwood GA）
grpc = "1.78.0"  # 変更なし
spring-grpc = "1.0.1"  # 0.12.0 → 1.0.1
```

### 変更の根拠

- **Spring Boot 4.0.2**: 2026年1月22日リリースの最新安定版。88件のバグ修正と依存関係アップグレードを含む
- **Spring Cloud 2025.1.0**: Spring Boot 4対応のOakwoodリリース。Spring Framework 7ベース、Jackson 3サポート
- **Spring gRPC 1.0.1**: Spring Boot 4対応の最初のメンテナンスリリース。0.12はSpring Boot 4非対応
- **gRPC 1.78.0**: 現行バージョンを維持

### その他の依存関係

以下は要確認項目（Spring Boot 4のBOMで管理される可能性）:
- springdoc-openapi
- micrometer-tracing-bridge
- opentelemetry-*

## 影響範囲の分析

### プロジェクト構成

このプロジェクトは以下の特性を持つ:
- データベース不使用（インメモリデータのみ）
- Jakarta Annotationは既に使用中（jakarta.annotation.PostConstruct）
- JPA/Hibernate未使用

そのため、Jakarta EE 11やHibernate 7への移行は不要。

### 影響を受けるモジュール

**高リスク**
1. **application-grpc-server**: Spring gRPC 1.0の破壊的変更の可能性
2. **application-client**: gRPCクライアント設定、RestClient設定の変更可能性

**中リスク**
3. **application-gateway-server**: Spring Cloud Gateway 2025.1での変更
4. **application-server**: OpenAPI設定、Micrometer設定の変更可能性

**低リスク**
5. **application-spring-cloud-aws-client**: AWS SDKは独立しているため影響少
6. **campaign/campaign-proto**: ドメインロジックのみなので影響最小

### 想定される変更箇所

- gRPCサーバー/クライアントの設定（application.yaml）
- OpenTelemetry関連の設定
- SpringDocの設定（OpenAPI）

## 移行手順

### フェーズ1: 準備（5-10分）

1. 新しいブランチ作成
   ```bash
   git checkout -b feature/spring-boot-4-migration
   ```

2. 現在の状態でテスト実行（ベースライン記録）
   ```bash
   ./gradlew clean test
   ```

3. `libs.versions.toml`のバックアップ確認

### フェーズ2: 依存関係更新（5分）

1. `libs.versions.toml`を更新
   - spring-boot: 4.0.2
   - spring-cloud: 2025.1.0
   - spring-grpc: 1.0.1

2. Gradle同期
   ```bash
   ./gradlew --refresh-dependencies
   ```

3. ビルド確認
   ```bash
   ./gradlew clean build
   ```

### フェーズ3: エラー修正（時間不明）

コンパイルエラーがあれば修正:
- Spring gRPC 0.12→1.0.1の設定変更
- 非推奨APIの置き換え
- Jackson 3への変更に伴う調整（もしあれば）

### フェーズ4: テスト実行（10-15分）

1. 全テスト実行
   ```bash
   ./gradlew test
   ```

2. 各モジュールのテスト結果確認

3. 失敗したテストの原因調査と修正

### フェーズ5: 手動検証（15-20分）

各アプリケーションの起動確認:
1. application-server
2. application-grpc-server
3. application-gateway-server
4. application-client
5. OpenAPI UI確認（http://localhost:8080/swagger-ui.html）
6. Actuator/Metricsエンドポイント確認

## 検証チェックリスト

### ビルドとテスト
- [ ] 全モジュールがコンパイル成功
- [ ] 全ユニットテスト/統合テストがパス
- [ ] ArchUnitのアーキテクチャテストがパス
- [ ] Karateの統合テストがパス

### 各アプリケーションの起動確認
- [ ] application-server: ポート8080で起動、ヘルスチェック正常
- [ ] application-grpc-server: gRPCサービス起動、通信可能
- [ ] application-gateway-server: ゲートウェイ起動、ルーティング正常
- [ ] application-client: 各サーバーへの接続成功

### 機能確認
- [ ] REST API動作（キャンペーン一覧取得、詳細取得）
- [ ] gRPC通信動作
- [ ] OpenAPI UI表示（Swagger UI）
- [ ] Actuatorエンドポイント（/actuator/health, /actuator/prometheus）
- [ ] トレーシング（Zipkinへのトレース送信）

## ロールバック計画

### 問題が発生した場合

1. ブランチを削除
   ```bash
   git branch -D feature/spring-boot-4-migration
   ```

2. 元のmainブランチに戻る
   ```bash
   git checkout main
   ```

3. 問題を記録し、後日再挑戦

### 段階的ロールバック

特定のモジュールだけ問題がある場合、そのモジュールのみ旧バージョンに戻すことも可能（libs.versions.tomlで個別バージョン指定）。

## 参考資料

- [Spring Boot 4.0.2 Release](https://spring.io/blog/2026/01/22/spring-boot-4-0-2-available-now)
- [Spring Boot 4.0 Migration Guide](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-4.0-Migration-Guide)
- [Spring Cloud 2025.1.0 (Oakwood)](https://spring.io/blog/2025/11/25/spring-cloud-2025-1-0-aka-oakwood-has-been-released/)
- [Spring gRPC 1.0.1](https://spring.io/blog/2026/01/07/spring-grpc-1/)
