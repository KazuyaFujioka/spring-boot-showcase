package com.example.domain.model;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Objects;

/** 管理番号 */
public class Number {

  @JsonValue String value;

  // GET時にコンストラクタを使用するのでpublicにしておかないと落ちる
  public Number(String value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Number number = (Number) o;
    return Objects.equals(value, number.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return value;
  }

  Number() {}
}
