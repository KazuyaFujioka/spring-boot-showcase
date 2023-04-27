package com.example.domain.model;

import java.time.LocalDateTime;

public class CampaignFactory {

  // RDSとか使用しないので用意している
  public static Campaign create(
      String title, LocalDateTime start, LocalDateTime end, String number) {
    Period period = PeriodFactory.create(start, end);
    return new Campaign(new Title(title), period, new Number(number));
  }
}
