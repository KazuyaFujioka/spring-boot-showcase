#### Tips
[JIG](https://github.com/dddjava/jig)と言うプラグインを利用してcampaignモジュールのソースコードから設計情報を出力できるようにしている。
```
./gradlew -x test clean build jig
```
を実行することでcampaignモジュールのbuildディレクトリ内にjigフォルダが作成される。
- index.html経由で各種情報を閲覧可能
- **注意:** JIGはclass情報を解析に利用するので実施前に**ソースコードコンパイル**が必須
