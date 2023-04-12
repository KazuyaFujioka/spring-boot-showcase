package com.example.domain.model;

import java.time.LocalDateTime;

public class CampaignFactory {

  // RDSとか使用しないので用意している
  public static Campaign create(
      String title, LocalDateTime start, LocalDateTime end, String number) {
    return new Campaign(new Title(title), new Period(start, end), new Number(number));
  }
}
