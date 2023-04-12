package com.example.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

/** キャンペーン実施期間 */
class Period {

  @JsonProperty LocalDateTime start;

  @JsonProperty LocalDateTime end;

  Period(LocalDateTime start, LocalDateTime end) {
    this.start = start;
    this.end = end;
  }

  Status toStatus() {
    LocalDateTime current = LocalDateTime.now();
    if (start.isAfter(current)) return Status.実施前;
    if (end.isBefore(current)) return Status.終了;
    return Status.実施中;
  }

  @Override
  public String toString() {
    return String.format("%s〜%s", start, end);
  }

  Period() {}
}
