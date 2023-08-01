# protobufの静的ドキュメントの作成方法

protoファイルからコードを自動生成するタイミングと同時にドキュメントがbuild/generated/source以下に作成される

手動で生成したい場合は以下のコマンドを実行することで作成可能
```shell
./gradlew :campaign-proto:generateProto
```
