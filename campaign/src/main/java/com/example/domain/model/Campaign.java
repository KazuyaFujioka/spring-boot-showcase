package com.example.domain.model;

/** キャンペーン */
public class Campaign {

  Title title;
  Period period;
  Number number;

  Campaign(Title title, Period period, Number number) {
    this.title = title;
    this.period = period;
    this.number = number;
  }

  public Number number() {
    return number;
  }

  @Override
  public String toString() {
    return "Campaign{" + "title=" + title + ", period=" + period + ", number=" + number + '}';
  }

  Campaign() {}
}
