package com.example.domain.model;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

@DisplayName("キャンペーン実施期間のテストケース")
class PeriodTest {

  static MockedStatic<LocalDateTime> mock;

  @BeforeAll
  static void setup() {
    LocalDateTime systemDate = LocalDateTime.of(2023, 2, 1, 0, 0, 0, 0);
    mock = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS);
    mock.when(LocalDateTime::now).thenReturn(systemDate);
  }

  @Test
  @DisplayName("キャンペーン実施期間が期間中のものはキャンペーン実施状況が実施中となる")
  void onGoingCampaignStatusValid() {
    LocalDateTime start = LocalDateTime.of(2023, 2, 1, 0, 0, 0, 0);
    LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59, 59, 999);

    Period period = PeriodFactory.create(start, end);

    Status expect = Status.実施中;
    Assertions.assertEquals(expect, period.toStatus());
  }

  @Test
  @DisplayName("キャンペーン実施期間が期間前のものはキャンペーン実施状況が実施前となる")
  void beforeCampaignStatusInValid() {
    LocalDateTime start = LocalDateTime.of(2023, 2, 2, 0, 0, 0, 0);
    LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59, 59, 999);

    Period period = PeriodFactory.create(start, end);

    Status expect = Status.実施前;
    Assertions.assertEquals(expect, period.toStatus());
  }

  @Test
  @DisplayName("キャンペーン実施期間が期間後のものはキャンペーン実施状況が終了となる")
  void afterCampaignStatusInValid() {
    LocalDateTime start = LocalDateTime.of(2022, 2, 1, 0, 0, 0, 0);
    LocalDateTime end = LocalDateTime.of(2022, 12, 31, 23, 59, 59, 999);

    Period period = PeriodFactory.create(start, end);

    Status expect = Status.終了;
    Assertions.assertEquals(expect, period.toStatus());
  }

  @Test
  @DisplayName("開始日時と終了日時が同じ場合は実施中となる")
  void sameDateTimeStatus() {
    LocalDateTime sameTime = LocalDateTime.of(2023, 2, 1, 0, 0, 0, 0);
    Period period = PeriodFactory.create(sameTime, sameTime);

    Status expect = Status.実施中;
    Assertions.assertEquals(expect, period.toStatus());
  }

  @Test
  @DisplayName("現在時刻がマイクロ秒レベルで境界値の場合の動作")
  void microsecondBoundaryTest() {
    // より精密な境界値テスト
    LocalDateTime start = LocalDateTime.of(2023, 2, 1, 0, 0, 0, 0);
    LocalDateTime end = LocalDateTime.of(2023, 2, 1, 0, 0, 0, 1000000); // 1秒後

    Period period = PeriodFactory.create(start, end);
    Status expect = Status.実施中;
    Assertions.assertEquals(expect, period.toStatus());
  }

  @Test
  @DisplayName("開始日が終了日より後の場合でも期間オブジェクトを作成できる")
  void invalidDateRangeCreation() {
    LocalDateTime start = LocalDateTime.of(2023, 12, 31, 23, 59);
    LocalDateTime end = LocalDateTime.of(2023, 1, 1, 0, 0);

    Period period = PeriodFactory.create(start, end);

    // 現在の実装では日付の妥当性チェックがないため、作成できることを確認
    Assertions.assertNotNull(period);
    Assertions.assertTrue(period.start().isAfter(period.end()));
  }

  @Test
  @DisplayName("極端な日時でも期間オブジェクトを作成できる")
  void extremeDateTimeCreation() {
    LocalDateTime start = LocalDateTime.MIN;
    LocalDateTime end = LocalDateTime.MAX;

    Period period = PeriodFactory.create(start, end);

    Assertions.assertNotNull(period);
    Assertions.assertNotNull(period.start());
    Assertions.assertNotNull(period.end());
  }

  @Test
  @DisplayName("期間の文字列表現が正しい形式で出力される")
  void toStringFormat() {
    LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
    LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59);

    Period period = PeriodFactory.create(start, end);
    String result = period.toString();

    Assertions.assertTrue(result.contains("2023-01-01T00:00Z"));
    Assertions.assertTrue(result.contains("2023-12-31T23:59Z"));
    Assertions.assertTrue(result.contains("〜"));
  }

  @Test
  @DisplayName("UTCタイムゾーンで期間が作成される")
  void utcTimezoneCreation() {
    LocalDateTime start = LocalDateTime.of(2023, 6, 15, 12, 30);
    LocalDateTime end = LocalDateTime.of(2023, 6, 15, 18, 30);

    Period period = PeriodFactory.create(start, end);

    Assertions.assertEquals("Z", period.start().getOffset().toString());
    Assertions.assertEquals("Z", period.end().getOffset().toString());
  }

  @Test
  @DisplayName("期間の等価性が正しく判定される")
  void periodEquality() {
    LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
    LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59);

    Period period1 = PeriodFactory.create(start, end);
    Period period2 = PeriodFactory.create(start, end);

    Assertions.assertEquals(period1, period2);
    Assertions.assertEquals(period1.hashCode(), period2.hashCode());
  }
}
