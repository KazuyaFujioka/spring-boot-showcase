### 中間(CA)証明書の秘密鍵(ca-key.pem)と公開鍵(ca-cert.pem)を作成
```
openssl req -x509 \
-newkey rsa:4096 \
-sha256 \
-nodes \
-days 3650 \
-keyout ca-key.pem \
-out ca-cert.pem \
-subj "/C=JP/ST=Tokyo/L=Tokyo/O=Private/OU=Sandbox/CN=localhost/emailAddress=localhost@example.com"
```

### アプリケーションサーバー用の秘密鍵(server-key.pem)と証明書の署名リクエスト(server-req.pem)を作成
```
openssl req -newkey rsa:4096 \
-nodes \
-keyout server-key.pem \
-out server-req.pem \
-subj "/C=JP/ST=Tokyo/L=Tokyo/O=Private/OU=Sandbox/CN=localhost/emailAddress=localhost@example.com"
```

### 中間(CA)証明書の秘密鍵(ca-key.pem)と公開鍵(ca-cert.pem)とアプリケーションサーバー用の証明書の署名リクエスト(server-req.pem)を使ってアプリケーションサーバー用の公開鍵(server-cert.pem)を作成
```
openssl x509 -req \
-days 3650 \
-CA ca-cert.pem \
-CAkey ca-key.pem \
-CAcreateserial \
-in server-req.pem \
-out server-cert.pem \
-extfile conf/server-ext.conf
```

### クライアント用の秘密鍵(client-key.pem)と証明書の署名リクエスト(client-req.pem)を作成
```
openssl req -newkey rsa:4096 \
-nodes \
-keyout client-key.pem \
-out client-req.pem \
-subj "/C=JP/ST=Tokyo/L=Tokyo/O=Private/OU=Sandbox/CN=localhost/emailAddress=localhost@example.com"
```

### 中間(CA)証明書の秘密鍵(ca-key.pem)と公開鍵(ca-cert.pem)とクライアント用の証明書の署名リクエスト(client-req.pem)を使ってクライアント用の公開鍵(client-cert.pem)を作成
```
openssl x509 -req \
-days 3650 \
-CA ca-cert.pem \
-CAkey ca-key.pem \
-CAcreateserial \
-in client-req.pem \
-out client-cert.pem \
-extfile conf/client-ext.conf
```

### クライアント用の秘密鍵(client-key.pem)と公開鍵(client-cert.pem)からpkcs12形式のファイル(client-cert.p12)を作成
```
openssl pkcs12 \
-export \
-in client-cert.pem \
-inkey client-key.pem \
-out client-cert.p12
```

password entry
```
password
```

### 自前で作成したSSL用証明書を使ってSSLアクセスした際にアラートを出ないようにする設定(Macのみ)
1. パスワードチェインにclient-cert.pemを追加する
2. localhostで登録された情報の詳細を表示し信頼を開く
3. 常に信頼に変更する
