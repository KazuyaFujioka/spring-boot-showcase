package com.example.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("キャンペーン名のテストケース")
class TitleTest {

  @Test
  @DisplayName("正常な文字列でTitleを作成できる")
  void createValidTitle() {
    String value = "春のキャンペーン";
    Title title = new Title(value);
    assertEquals(value, title.value());
    assertEquals(value, title.toString());
  }

  @Test
  @DisplayName("空文字列でもTitleを作成できる")
  void createEmptyTitle() {
    Title title = new Title("");
    assertEquals("", title.value());
    assertEquals("", title.toString());
  }

  @Test
  @DisplayName("nullでTitleを作成した場合の動作")
  void createNullTitle() {
    // 現在の実装では null チェックがないため、動作を確認
    Title title = new Title(null);
    assertNull(title.value());
    assertNull(title.toString());
  }

  @Test
  @DisplayName("同じ値のTitleは等価である")
  void equalTitles() {
    Title title1 = new Title("テストキャンペーン");
    Title title2 = new Title("テストキャンペーン");
    assertEquals(title1, title2);
    assertEquals(title1.hashCode(), title2.hashCode());
  }

  @Test
  @DisplayName("異なる値のTitleは等価でない")
  void notEqualTitles() {
    Title title1 = new Title("春のキャンペーン");
    Title title2 = new Title("夏のキャンペーン");
    assertNotEquals(title1, title2);
  }

  @Test
  @DisplayName("特殊文字を含むTitleを作成できる")
  void createTitleWithSpecialCharacters() {
    String value = "【特別】春の大感謝祭！50%OFF★";
    Title title = new Title(value);
    assertEquals(value, title.value());
    assertEquals(value, title.toString());
  }

  @Test
  @DisplayName("長い文字列でTitleを作成できる")
  void createLongTitle() {
    String value = "非常に長いキャンペーン名".repeat(10);
    Title title = new Title(value);
    assertEquals(value, title.value());
    assertEquals(value, title.toString());
  }
}
