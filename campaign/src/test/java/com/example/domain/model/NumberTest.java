package com.example.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("管理番号のテストケース")
class NumberTest {

  @Test
  @DisplayName("正常な文字列でNumberを作成できる")
  void createValidNumber() {
    String value = "ABC123";
    Number number = new Number(value);
    assertEquals(value, number.value());
    assertEquals(value, number.toString());
  }

  @Test
  @DisplayName("空文字列でもNumberを作成できる")
  void createEmptyNumber() {
    Number number = new Number("");
    assertEquals("", number.value());
    assertEquals("", number.toString());
  }

  @Test
  @DisplayName("nullでNumberを作成した場合の動作")
  void createNullNumber() {
    // 現在の実装では null チェックがないため、動作を確認
    Number number = new Number(null);
    assertNull(number.value());
    assertNull(number.toString());
  }

  @Test
  @DisplayName("同じ値のNumberは等価である")
  void equalNumbers() {
    Number number1 = new Number("ABC123");
    Number number2 = new Number("ABC123");
    assertEquals(number1, number2);
    assertEquals(number1.hashCode(), number2.hashCode());
  }

  @Test
  @DisplayName("異なる値のNumberは等価でない")
  void notEqualNumbers() {
    Number number1 = new Number("ABC123");
    Number number2 = new Number("XYZ789");
    assertNotEquals(number1, number2);
  }

  @Test
  @DisplayName("数字のみの管理番号を作成できる")
  void createNumericNumber() {
    String value = "123456789";
    Number number = new Number(value);
    assertEquals(value, number.value());
    assertEquals(value, number.toString());
  }

  @Test
  @DisplayName("英字のみの管理番号を作成できる")
  void createAlphabeticNumber() {
    String value = "ABCDEFGHIJK";
    Number number = new Number(value);
    assertEquals(value, number.value());
    assertEquals(value, number.toString());
  }

  @Test
  @DisplayName("特殊文字を含む管理番号を作成できる")
  void createNumberWithSpecialCharacters() {
    String value = "ABC-123_XYZ";
    Number number = new Number(value);
    assertEquals(value, number.value());
    assertEquals(value, number.toString());
  }

  @Test
  @DisplayName("長い管理番号を作成できる")
  void createLongNumber() {
    String value = "VERY_LONG_CAMPAIGN_NUMBER_123456789";
    Number number = new Number(value);
    assertEquals(value, number.value());
    assertEquals(value, number.toString());
  }

  @Test
  @DisplayName("実際のデータソースで使用される管理番号形式をテスト")
  void createRealWorldNumber() {
    String value = "dOY0aXcFv3grfpT";
    Number number = new Number(value);
    assertEquals(value, number.value());
    assertEquals(value, number.toString());
  }
}
