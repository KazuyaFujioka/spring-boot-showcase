package com.example.domain.model;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

class PeriodFactory {

  static Period create(LocalDateTime start, LocalDateTime end) {
    return new Period(
        OffsetDateTime.of(start, ZoneOffset.UTC), OffsetDateTime.of(end, ZoneOffset.UTC));
  }
}
