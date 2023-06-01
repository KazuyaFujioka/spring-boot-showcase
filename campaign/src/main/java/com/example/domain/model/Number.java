package com.example.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

/** 管理番号 */
public record Number(@JsonProperty("value") @JsonValue String value) {

  @Override
  public String toString() {
    return value;
  }
}
