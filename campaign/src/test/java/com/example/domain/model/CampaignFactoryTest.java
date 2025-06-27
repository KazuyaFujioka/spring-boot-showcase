package com.example.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("キャンペーンファクトリのテストケース")
class CampaignFactoryTest {

  @Test
  @DisplayName("正常なパラメータでキャンペーンを作成できる")
  void createValidCampaign() {
    String title = "テストキャンペーン";
    LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
    LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59);
    String number = "TEST001";

    Campaign campaign = CampaignFactory.create(title, start, end, number);

    assertEquals(title, campaign.title().value());
    assertEquals(number, campaign.number().value());
    assertNotNull(campaign.period());
    assertEquals(start.toString() + "Z", campaign.period().start().toString());
    assertEquals(end.toString() + "Z", campaign.period().end().toString());
  }

  @Test
  @DisplayName("開始日が終了日より後の場合でもキャンペーンを作成できる")
  void createCampaignWithInvalidDateRange() {
    LocalDateTime start = LocalDateTime.of(2023, 12, 31, 23, 59);
    LocalDateTime end = LocalDateTime.of(2023, 1, 1, 0, 0);

    // 現在の実装では日付の妥当性チェックがないため、作成できることを確認
    Campaign campaign = CampaignFactory.create("テスト", start, end, "TEST001");
    assertNotNull(campaign);
    assertTrue(campaign.period().start().isAfter(campaign.period().end()));
  }

  @Test
  @DisplayName("同じ開始日時と終了日時でキャンペーンを作成できる")
  void createCampaignWithSameStartAndEnd() {
    LocalDateTime sameTime = LocalDateTime.of(2023, 6, 15, 12, 0);

    Campaign campaign = CampaignFactory.create("同時刻キャンペーン", sameTime, sameTime, "SAME001");

    assertNotNull(campaign);
    assertEquals(campaign.period().start(), campaign.period().end());
  }

  @Test
  @DisplayName("nullタイトルでキャンペーンを作成できる")
  void createCampaignWithNullTitle() {
    LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
    LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59);

    Campaign campaign = CampaignFactory.create(null, start, end, "NULL001");

    assertNotNull(campaign);
    assertNull(campaign.title().value());
  }

  @Test
  @DisplayName("null管理番号でキャンペーンを作成できる")
  void createCampaignWithNullNumber() {
    LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
    LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59);

    Campaign campaign = CampaignFactory.create("テスト", start, end, null);

    assertNotNull(campaign);
    assertNull(campaign.number().value());
  }

  @Test
  @DisplayName("空文字列のタイトルと管理番号でキャンペーンを作成できる")
  void createCampaignWithEmptyStrings() {
    LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
    LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59);

    Campaign campaign = CampaignFactory.create("", start, end, "");

    assertNotNull(campaign);
    assertEquals("", campaign.title().value());
    assertEquals("", campaign.number().value());
  }

  @Test
  @DisplayName("実際のデータソースと同じ形式でキャンペーンを作成できる")
  void createRealWorldCampaign() {
    String title = "お友達紹介キャンペーン";
    LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
    LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59, 59, 999000000);
    String number = "dOY0aXcFv3grfpT";

    Campaign campaign = CampaignFactory.create(title, start, end, number);

    assertEquals(title, campaign.title().value());
    assertEquals(number, campaign.number().value());
    assertNotNull(campaign.period());
  }

  @Test
  @DisplayName("極端な日時でキャンペーンを作成できる")
  void createCampaignWithExtremeDates() {
    LocalDateTime start = LocalDateTime.MIN;
    LocalDateTime end = LocalDateTime.MAX;

    Campaign campaign = CampaignFactory.create("極端な期間", start, end, "EXTREME001");

    assertNotNull(campaign);
    assertEquals("極端な期間", campaign.title().value());
    assertEquals("EXTREME001", campaign.number().value());
  }

  @Test
  @DisplayName("特殊文字を含むタイトルでキャンペーンを作成できる")
  void createCampaignWithSpecialCharactersInTitle() {
    String title = "【特別】春の大感謝祭！50%OFF★";
    LocalDateTime start = LocalDateTime.of(2023, 3, 1, 0, 0);
    LocalDateTime end = LocalDateTime.of(2023, 5, 31, 23, 59);
    String number = "SPECIAL001";

    Campaign campaign = CampaignFactory.create(title, start, end, number);

    assertEquals(title, campaign.title().value());
    assertEquals(number, campaign.number().value());
  }
}
