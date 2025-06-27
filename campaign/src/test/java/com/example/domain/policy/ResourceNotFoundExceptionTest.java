package com.example.domain.policy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("リソース未発見例外のテストケース")
class ResourceNotFoundExceptionTest {

    @Test
    @DisplayName("メッセージ付きで例外を作成できる")
    void createExceptionWithMessage() {
        String message = "テストメッセージ";
        ResourceNotFoundException exception = new ResourceNotFoundException(message);

        assertEquals(message, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("空文字列のメッセージで例外を作成できる")
    void createExceptionWithEmptyMessage() {
        String message = "";
        ResourceNotFoundException exception = new ResourceNotFoundException(message);

        assertEquals(message, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("nullメッセージで例外を作成できる")
    void createExceptionWithNullMessage() {
        ResourceNotFoundException exception = new ResourceNotFoundException(null);

        assertNull(exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("日本語メッセージで例外を作成できる")
    void createExceptionWithJapaneseMessage() {
        String message = "キャンペーンが存在しません。管理番号: TEST001";
        ResourceNotFoundException exception = new ResourceNotFoundException(message);

        assertEquals(message, exception.getMessage());
        assertTrue(exception.getMessage().contains("キャンペーンが存在しません"));
        assertTrue(exception.getMessage().contains("TEST001"));
    }

    @Test
    @DisplayName("長いメッセージで例外を作成できる")
    void createExceptionWithLongMessage() {
        String message = "非常に長いエラーメッセージです。".repeat(100);
        ResourceNotFoundException exception = new ResourceNotFoundException(message);

        assertEquals(message, exception.getMessage());
        assertTrue(exception.getMessage().length() > 1000);
    }

    @Test
    @DisplayName("特殊文字を含むメッセージで例外を作成できる")
    void createExceptionWithSpecialCharacters() {
        String message = "エラー: 【重要】キャンペーン#123が見つかりません！@#$%^&*()";
        ResourceNotFoundException exception = new ResourceNotFoundException(message);

        assertEquals(message, exception.getMessage());
        assertTrue(exception.getMessage().contains("【重要】"));
        assertTrue(exception.getMessage().contains("#123"));
    }

    @Test
    @DisplayName("実際のデータソースで使用されるメッセージ形式をテスト")
    void createExceptionWithRealWorldMessage() {
        String number = "NONEXISTENT";
        String message = String.format("キャンペーンが存在しません。管理番号: %s", number);
        ResourceNotFoundException exception = new ResourceNotFoundException(message);

        assertEquals(message, exception.getMessage());
        assertTrue(exception.getMessage().contains("キャンペーンが存在しません"));
        assertTrue(exception.getMessage().contains("NONEXISTENT"));
    }

    @Test
    @DisplayName("例外のスタックトレースが正しく設定される")
    void exceptionStackTrace() {
        ResourceNotFoundException exception = new ResourceNotFoundException("テスト例外");

        assertNotNull(exception.getStackTrace());
        assertTrue(exception.getStackTrace().length > 0);
        assertEquals("exceptionStackTrace", exception.getStackTrace()[0].getMethodName());
    }

    @Test
    @DisplayName("例外の継承関係が正しい")
    void exceptionInheritance() {
        ResourceNotFoundException exception = new ResourceNotFoundException("テスト");

        assertTrue(exception instanceof RuntimeException);
        assertTrue(exception instanceof Exception);
        assertTrue(exception instanceof Throwable);
    }

    @Test
    @DisplayName("複数の例外インスタンスは独立している")
    void multipleExceptionInstances() {
        ResourceNotFoundException exception1 = new ResourceNotFoundException("メッセージ1");
        ResourceNotFoundException exception2 = new ResourceNotFoundException("メッセージ2");

        assertNotEquals(exception1, exception2);
        assertNotEquals(exception1.getMessage(), exception2.getMessage());
        assertEquals("メッセージ1", exception1.getMessage());
        assertEquals("メッセージ2", exception2.getMessage());
    }
}
