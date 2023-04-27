package com.example.domain.model;

import com.fasterxml.jackson.annotation.JsonValue;

/** キャンペーン名 */
record Title(@JsonValue String value) {

  @Override
  public String toString() {
    return value;
  }
}
