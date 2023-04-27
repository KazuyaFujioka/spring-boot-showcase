package com.example.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/** キャンペーン実施期間 */
// gRPCとのマッピングがRFC3339でしかできないのでOffsetDateTimeを使用する
record Period(
    @JsonFormat(shape = JsonFormat.Shape.STRING) OffsetDateTime start,
    @JsonFormat(shape = JsonFormat.Shape.STRING) OffsetDateTime end) {

  Status toStatus() {
    OffsetDateTime current = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC);
    if (start.isAfter(current)) return Status.実施前;
    if (end.isBefore(current)) return Status.終了;
    return Status.実施中;
  }

  @Override
  public String toString() {
    return String.format("%s〜%s", start, end);
  }
}
