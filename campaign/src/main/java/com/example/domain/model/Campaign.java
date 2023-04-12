package com.example.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/** キャンペーン */
public class Campaign {

  @JsonProperty Title title;

  @JsonProperty Period period;

  @JsonProperty Number number;

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
