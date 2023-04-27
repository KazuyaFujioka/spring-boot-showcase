package com.example.domain.model;

import com.fasterxml.jackson.annotation.JsonValue;

/** 管理番号 */
public record Number(@JsonValue String value) {

  @Override
  public String toString() {
    return value;
  }
}
