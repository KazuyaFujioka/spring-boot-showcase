package com.example;

import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class S3SimpleClient {

  Logger LOG = LoggerFactory.getLogger(S3SimpleClient.class);

  S3Template s3Template;
  String bucket;

  File download(S3ObjectKey key) {
    S3Resource resource = s3Template.download(bucket, key.toString());
    try (InputStream inputStream = resource.getInputStream()) {
      LOG.info("name:{}, size:{}", resource.getFilename(), resource.contentLength());
      File file = new File(resource.getFilename());
      Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
      return file;
    } catch (IOException exception) {
      String message = String.format("bucket:%s file:%s is not found.", bucket, key);
      throw new IllegalArgumentException(message);
    }
  }

  S3SimpleClient(S3Template s3Template, String bucket) {
    this.s3Template = s3Template;
    this.bucket = bucket;
  }
}
