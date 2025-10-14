profileを指定しているのでコマンドでdocker全てを起動する場合は以下を実行する
```shell
docker compose --profile default up -d
```

停止する場合は以下を実行する
```shell
docker compose --profile default down
```
