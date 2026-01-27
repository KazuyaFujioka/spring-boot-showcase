---
name: spring-boot-showcase-library-updater
description: Spring Boot Showcaseプロジェクト専用のライブラリ更新スキル。プロジェクトの制約（Spring Boot 3系維持、依存関係の考慮）を守りながら、ライブラリを最新化する。使用タイミング：(1)「ライブラリを最新化してください」「依存関係を更新してください」などのライブラリ更新要求時、(2) Spring Boot Showcaseプロジェクト内での作業時、(3) libs.versions.tomlの更新が必要な時。このスキルは/Users/kazuya.fujioka/Workingspace/sandbox/spring-boot-showcaseディレクトリでのみ使用する。
---

# Spring Boot Showcase Library Updater

このスキルは、spring-boot-showcaseプロジェクト専用のライブラリ更新ワークフローを提供します。

## 重要な制約

作業前に必ず [constraints.md](references/constraints.md) を確認してください：
- Spring Boot 3系を維持（4系には更新しない）
- 依存関係のあるライブラリは慎重に更新
- 各更新後に必ずテストを実行

## ワークフロー

### 1. 現在の状態確認

```bash
cd /Users/kazuya.fujioka/Workingspace/sandbox/spring-boot-showcase
cat libs.versions.toml
git status
```

### 2. TODOタスクの作成

ライブラリを調査して、更新可能なものをTODOタスクとして作成：

```markdown
TaskCreate で以下の形式のタスクを作成：
- subject: "Update [ライブラリ名] to [バージョン]"
- description: 詳細な更新内容と注意点
- activeForm: "Updating [ライブラリ名] to [バージョン]"
```

**カテゴリ別のグルーピング例**：
- フレームワーク: Spring Boot, Spring Cloud, Spring Cloud AWS
- gRPC関連: gRPC, Protobuf, OpenTelemetry
- テスト: Karate, Cucumber Reporting, ArchUnit
- ドキュメント: SpringDoc OpenAPI, JIG
- ビルドツール: Gradle, Spotless

**ライブラリ情報の確認**：
[library-structure.md](references/library-structure.md) を参照して各ライブラリの更新確認先と依存関係を確認。

### 3. 作業ブランチの作成

```bash
git checkout -b feature/update-libraries-[カテゴリ]
```

### 4. ライブラリの更新（反復）

各タスクについて以下を実行：

#### 4.1 タスクを in_progress に設定

```markdown
TaskUpdate taskId=[ID] status=in_progress
```

#### 4.2 バージョン更新

`libs.versions.toml` を編集：

```bash
# 通常のライブラリ
Edit libs.versions.toml:
  old_string: '[library] = "old-version"'
  new_string: '[library] = "new-version"'

# Gradleの場合
./gradlew wrapper --gradle-version [VERSION]
```

#### 4.3 テスト実行

```bash
./gradlew clean test
```

**テストが失敗した場合**：
1. エラーを分析
2. 依存関係の問題であれば、バージョンを戻す
3. 破壊的変更であれば、タスクに記録して次へ
4. 必要に応じて段階的に古いバージョンで試す

**テストが成功した場合**：
次のステップへ進む

#### 4.4 変更をコミット

```bash
git add libs.versions.toml [その他の変更ファイル]
git commit -m "$(cat <<'EOF'
Update [ライブラリ名] to [バージョン]

- Update [ライブラリ名] from [旧バージョン] to [新バージョン]
- [追加情報があれば記載]
- All tests passed successfully

Co-Authored-By: Claude Sonnet 4.5 <noreply@anthropic.com>
EOF
)"
```

#### 4.5 タスクを completed に設定

```markdown
TaskUpdate taskId=[ID] status=completed
```

#### 4.6 次のタスクへ

次のタスクがある場合は 4.1 に戻る

### 5. コミット履歴の確認

```bash
git log --oneline --graph main..HEAD
```

複数のコミットが適切にグルーピングされていることを確認。

### 6. 最終テスト

全ての更新が完了したら、最終テストを実行：

```bash
./gradlew clean test
```

### 7. プルリクエストの作成

#### 7.1 リモートにプッシュ

```bash
git push -u origin feature/update-libraries-[カテゴリ]
```

**認証エラーが発生した場合**：
ユーザーに手動でのプッシュを依頼。

#### 7.2 PRの作成

```bash
gh pr create --title "ライブラリを最新化" --body "$(cat <<'EOF'
## 概要

Spring Boot 3系の最新版を維持しながら、利用しているライブラリを最新化しました。
Spring Boot 4系への更新は行わず、3.x系を維持しています。

## 更新内容（N個）

### [カテゴリ1]
- **[ライブラリ名]**: [旧バージョン] → [新バージョン]

### [カテゴリ2]
- **[ライブラリ名]**: [旧バージョン] → [新バージョン]

[全ての更新を記載]

## 更新を見送ったライブラリ

- **[ライブラリ名]**: [理由]

## テスト結果

✅ 全てのテストが成功
- 各コミットごとに`./gradlew clean test`を実行して確認
- テストケースの修正は不要（既存のテストが全て通過）

## その他

- 意味のある単位でコミットをグルーピング化
- 各コミットは独立してテスト済み
- 破壊的変更なし

🤖 Generated with Claude Code
EOF
)"
```

**gh コマンドが使えない場合**：
GitHub UIでのPR作成をユーザーに案内し、上記の本文テンプレートを提供。

## よくある問題と対処法

### テストが失敗する

1. **依存関係の問題**：関連ライブラリのバージョンを確認
2. **破壊的変更**：段階的に古いバージョンで試す
3. **互換性の問題**：[constraints.md](references/constraints.md) の既知の制約を確認

### 更新を見送る判断

以下の場合は更新を見送り、理由を記録：
- Spring Boot 4系が必要
- 依存関係で互換性がない
- テストが失敗し、修正が困難
- alpha/beta版で破壊的変更がある

### バージョン確認先

各ライブラリの更新確認先は [library-structure.md](references/library-structure.md) を参照。

## 完了条件

- [ ] 全てのタスクが completed
- [ ] 全てのテストが成功
- [ ] コミット履歴が適切にグルーピング化
- [ ] プルリクエストが作成済み

## 参照ドキュメント

- [constraints.md](references/constraints.md) - プロジェクト制約事項
- [library-structure.md](references/library-structure.md) - libs.versions.toml構造とライブラリ説明