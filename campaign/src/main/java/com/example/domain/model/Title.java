package com.example.domain.model;

import com.fasterxml.jackson.annotation.JsonValue;

/** キャンペーン名 */
class Title {

  @JsonValue String value;

  Title(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }

  Title() {}
}
