package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationSpringCloudAws implements ApplicationRunner {

  // とりあえず以下を用意する
  // TODO S3へのアップロード(LocalStack利用
  // シンプル
  // レガシーモード
  // 最新V3モード
  // 暗号化
  // レガシーモード
  // 最新V3モード
  // TODO S3からのダウンロード(LocalStack利用
  // シンプル
  // レガシーモード
  // 最新V3モード
  // 暗号化
  // レガシーモード
  // 最新V3モード
  // TODO SQSのpublish(LocalStack利用
  // TODO SQSのsubscribe(LocalStack利用
  // FIXME TestContainer使ってみる？

  Logger LOG = LoggerFactory.getLogger(ApplicationSpringCloudAws.class);

  public static void main(String[] args) {
    SpringApplication.run(ApplicationSpringCloudAws.class, args);
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {}

  ApplicationSpringCloudAws() {}
}
