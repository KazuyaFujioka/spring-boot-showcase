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

    Period period = new Period(start, end);

    Status expect = Status.実施中;
    Assertions.assertEquals(expect, period.toStatus());
  }

  @Test
  @DisplayName("キャンペーン実施期間が期間前のものはキャンペーン実施状況が実施前となる")
  void beforeCampaignStatusInValid() {
    LocalDateTime start = LocalDateTime.of(2023, 2, 2, 0, 0, 0, 0);
    LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59, 59, 999);

    Period period = new Period(start, end);

    Status expect = Status.実施前;
    Assertions.assertEquals(expect, period.toStatus());
  }

  @Test
  @DisplayName("キャンペーン実施期間が期間後のものはキャンペーン実施状況が終了となる")
  void afterCampaignStatusInValid() {
    LocalDateTime start = LocalDateTime.of(2022, 2, 1, 0, 0, 0, 0);
    LocalDateTime end = LocalDateTime.of(2022, 12, 31, 23, 59, 59, 999);

    Period period = new Period(start, end);

    Status expect = Status.終了;
    Assertions.assertEquals(expect, period.toStatus());
  }
}
