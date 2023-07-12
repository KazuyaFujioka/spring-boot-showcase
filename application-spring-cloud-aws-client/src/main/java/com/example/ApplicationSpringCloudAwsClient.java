package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationSpringCloudAwsClient implements ApplicationRunner {

  // とりあえず以下を用意する
  // MinIOで構築
  // https://go-tech.blog/aws/s3-minio/
  // https://kazuhira-r.hatenablog.com/entry/2023/01/26/233603
  // TODO S3へのアップロード(MinIO利用
  // シンプル
  // 暗号化
  // レガシーモード
  // 最新V3モード
  // TODO S3からのダウンロード(MinIO利用
  // シンプル
  // 暗号化
  // レガシーモード
  // 最新V3モード
  // TODO SQSのpublish(LocalStack利用
  // TODO SQSのsubscribe(LocalStack利用

  Logger LOG = LoggerFactory.getLogger(ApplicationSpringCloudAwsClient.class);

  public static void main(String[] args) {
    SpringApplication.run(ApplicationSpringCloudAwsClient.class, args);
  }

  S3SimpleClient simpleClient;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    simpleClient.download(new S3ObjectKey("simple.csv"));

    // TODO MinIOでSSE-KMSの設定が必要(動かない
    // https://medium.com/swlh/encrypted-minio-storage-with-kms-setup-40d0a0eab1e9

    // FIXME 暗号化されたものを復号化した状態でダウンロードはできる
    //    String key = "SMD12/personal_customer_list_encrypted2.csv";
    //    try {
    //      File stored = new File("application-spring-cloud-aws/build",
    // "personal_customer_list.csv");
    //      S3Resource s3Resource = s3Template.download(bucket, key);
    //      OutputStream outputStream = new FileOutputStream(stored);
    //      StreamUtils.copy(s3Resource.getInputStream(), outputStream);
    //    } catch (Exception exception) {
    //      String message = String.format("S3から報告書(%s)の読み込み失敗", key);
    //      LOG.error(message, exception);
    //
    //      throw new RuntimeException(exception.getMessage());
    //    }

    // FIXME S3Templateはダメそう
    //  とりあえずこれでアップロードはできたから読み込みしてみる？
    //    try {
    //      File stored = new File("application-spring-cloud-aws/build",
    // "personal_customer_list.csv");
    //
    //      PutObjectRequest putObjectRequest = PutObjectRequest.builder()
    //              .bucket(bucket)
    //              .key("SMD12/personal_customer_list_encrypted3.csv")
    //              .acl(ObjectCannedACL.BUCKET_OWNER_FULL_CONTROL)
    //              .contentLength(stored.length())
    //              .build();
    //
    //      RequestBody requestBody = RequestBody.fromFile(stored);
    //      s3Client.putObject(putObjectRequest, requestBody);
    //    } catch (Exception exception) {
    //      String message = String.format("%sの書き込み失敗", "personal_customer_list3.csv");
    //      LOG.error(message, exception);
    //
    //      throw new RuntimeException(exception.getMessage());
    //    } finally {
    //      System.out.println("/// 終了 ///");
    //      // s3Client使うとアプリケーションが終了しないのでexitを指定
    //      System.exit(0);
    //    }

    //    TransferManager transferManager =
    //            TransferManagerBuilder.standard().withS3Client(s3Template).build();
    //    try {
    //      File stored = new File("application-spring-cloud-aws/build",
    // "personal_customer_list.csv");
    //      s3Template.putObject(bucket,"SMD12/personal_customer_list_encrypted.csv", stored);
    //
    //      ObjectMetadata metadata = new ObjectMetadata();
    //      metadata.setContentLength(stored.length());
    //
    //      PutObjectRequest putObjectRequest =
    //              new PutObjectRequest(
    //                      bucket, "SMD12/personal_customer_list_encrypted.csv", new
    // FileInputStream(stored), metadata);
    //      putObjectRequest.setCannedAcl(CannedAccessControlList.BucketOwnerFullControl);
    //
    //      Upload upload = transferManager.upload(putObjectRequest);
    //      upload.waitForCompletion();
    //
    //    } catch (Exception exception) {
    //      String message = String.format("%sの書き込み失敗", "personal_customer_list3.csv");
    //      LOG.error(message, exception);
    //      throw new RuntimeException(exception.getMessage());
    //    } finally {
    //      transferManager.shutdownNow();
    //    }
  }

  ApplicationSpringCloudAwsClient(S3SimpleClient simpleClient) {
    this.simpleClient = simpleClient;
  }
}
