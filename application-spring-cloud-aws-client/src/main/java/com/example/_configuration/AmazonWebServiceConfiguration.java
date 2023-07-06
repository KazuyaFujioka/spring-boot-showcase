package com.example._configuration;

// import com.amazonaws.auth.AWSStaticCredentialsProvider;
// import com.amazonaws.auth.BasicAWSCredentials;
// import com.amazonaws.regions.Regions;
// import com.amazonaws.services.kms.AWSKMS;
// import com.amazonaws.services.kms.AWSKMSClientBuilder;
// import com.amazonaws.services.s3.AmazonS3;
// import com.amazonaws.services.s3.AmazonS3EncryptionClientV2Builder;
// import com.amazonaws.services.s3.model.CryptoConfigurationV2;
// import com.amazonaws.services.s3.model.CryptoMode;
// import com.amazonaws.services.s3.model.EncryptionMaterials;
// import com.amazonaws.services.s3.model.StaticEncryptionMaterialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// import software.amazon.encryption.s3.S3EncryptionClient;

// import software.amazon.encryption.s3.S3EncryptionClient;
// import software.amazon.encryption.s3.materials.CryptographicMaterialsManager;
// import software.amazon.encryption.s3.materials.DefaultCryptoMaterialsManager;
// import software.amazon.encryption.s3.materials.KmsKeyring;

@Configuration
class AmazonWebServiceConfiguration {

  //  @Bean
  //  S3Client s3Client(@Value("${spring.cloud.aws.kms.master-key:}") String masterKey) {
  //    // V1の場合
  //    // https://github.com/aws/amazon-s3-encryption-client-java#v1-key-materials-provider-to-v3
  //    SecretKey aesKey = new SecretKeySpec(Base64.getDecoder().decode(masterKey), "AES");
  //    return S3EncryptionClient.builder()
  //        .aesKey(aesKey)
  //        .enableLegacyUnauthenticatedModes(true)
  //        .enableLegacyWrappingAlgorithms(true)
  //        .build();
  //  }

  //    @Bean
  //    AmazonS3 s3Template(
  //            @Value("${cloud.aws.kms.master-key:}") String key,
  //            AmazonS3 amazonS3) {
  //        if (key.isEmpty()) return amazonS3;
  //
  //        EncryptionMaterials encryptionMaterials =
  //                new EncryptionMaterials(new SecretKeySpec(Base64.getDecoder().decode(key),
  // "AES"));
  //
  //            return AmazonS3EncryptionClientV2Builder.standard()
  //                .withRegion(Regions.AP_NORTHEAST_1)
  //                .withCryptoConfiguration(
  //                    new
  // CryptoConfigurationV2().withCryptoMode(CryptoMode.AuthenticatedEncryption))
  //                .withEncryptionMaterialsProvider(new
  //                        StaticEncryptionMaterialsProvider(encryptionMaterials))
  //                .build();
  //
  //        // FIXME AmazonS3EncryptionClientBuilderにしておかないと読み込み側でエラーになるかもしれない
  //        //  ただしAmazonS3EncryptionClientBuilderは既にDeprecated扱い
  ////        return AmazonS3EncryptionClientBuilder.standard()
  ////                .withKmsClient(awskms)
  ////                .withRegion(Regions.AP_NORTHEAST_1)
  ////                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
  ////                .withEncryptionMaterials(new
  // StaticEncryptionMaterialsProvider(encryptionMaterials))
  ////                .build();
  //    }

  @Bean
  String bucket(@Value("${spring.cloud.aws.s3.bucket:}") String bucket) {
    return bucket;
  }
}
