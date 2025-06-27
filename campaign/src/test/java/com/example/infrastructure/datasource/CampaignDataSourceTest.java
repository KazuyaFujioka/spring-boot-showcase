package com.example.infrastructure.datasource;

import com.example.domain.model.Campaign;
import com.example.domain.model.Campaigns;
import com.example.domain.model.Number;
import com.example.domain.policy.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("キャンペーンデータソースのテストケース")
class CampaignDataSourceTest {

    private CampaignDataSource campaignDataSource;

    @BeforeEach
    void setUp() {
        campaignDataSource = new CampaignDataSource();
        campaignDataSource.init(); // @PostConstruct メソッドを手動実行
    }

    @Test
    @DisplayName("実施中キャンペーン一覧を取得できる")
    void findOngoingCampaigns() {
        Campaigns result = campaignDataSource.findOngoingCampaigns();

        assertNotNull(result);
        assertEquals(2, result.list().size());

        // 具体的なキャンペーン内容の検証（管理番号で確認）
        assertTrue(result.list().stream()
            .anyMatch(c -> "0nss8Qp2du2cSWT".equals(c.number().value())));
        assertTrue(result.list().stream()
            .anyMatch(c -> "dOY0aXcFv3grfpT".equals(c.number().value())));
    }

    @Test
    @DisplayName("実施中キャンペーン一覧の各キャンペーンが正しい構造を持つ")
    void findOngoingCampaignsStructure() {
        Campaigns result = campaignDataSource.findOngoingCampaigns();

        result.list().forEach(campaign -> {
            assertNotNull(campaign.title());
            assertNotNull(campaign.period());
            assertNotNull(campaign.number());
            assertNotNull(campaign.number().value());
        });
    }

    @Test
    @DisplayName("存在する管理番号でキャンペーンを取得できる")
    void findExistingCampaign() {
        Number number = new Number("dOY0aXcFv3grfpT");

        Campaign result = campaignDataSource.findCampaign(number);

        assertNotNull(result);
        assertEquals("dOY0aXcFv3grfpT", result.number().value());
        assertNotNull(result.title());
        assertNotNull(result.period());
    }

    @Test
    @DisplayName("もう一つの存在する管理番号でキャンペーンを取得できる")
    void findAnotherExistingCampaign() {
        Number number = new Number("0nss8Qp2du2cSWT");

        Campaign result = campaignDataSource.findCampaign(number);

        assertNotNull(result);
        assertEquals("0nss8Qp2du2cSWT", result.number().value());
        assertNotNull(result.title());
        assertNotNull(result.period());
    }

    @Test
    @DisplayName("存在しない管理番号でResourceNotFoundExceptionが発生する")
    void findNonExistingCampaign() {
        Number number = new Number("NONEXISTENT");

        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> campaignDataSource.findCampaign(number)
        );

        assertTrue(exception.getMessage().contains("キャンペーンが存在しません"));
        assertTrue(exception.getMessage().contains("NONEXISTENT"));
    }

    @Test
    @DisplayName("空文字列の管理番号でResourceNotFoundExceptionが発生する")
    void findCampaignWithEmptyNumber() {
        Number number = new Number("");

        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> campaignDataSource.findCampaign(number)
        );

        assertTrue(exception.getMessage().contains("キャンペーンが存在しません"));
        assertTrue(exception.getMessage().contains("管理番号:"));
    }

    @Test
    @DisplayName("null値の管理番号でResourceNotFoundExceptionが発生する")
    void findCampaignWithNullNumber() {
        Number number = new Number(null);

        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> campaignDataSource.findCampaign(number)
        );

        assertTrue(exception.getMessage().contains("キャンペーンが存在しません"));
    }

    @Test
    @DisplayName("大文字小文字が異なる管理番号でResourceNotFoundExceptionが発生する")
    void findCampaignWithDifferentCase() {
        Number number = new Number("doy0axcfv3grfpt"); // 小文字

        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> campaignDataSource.findCampaign(number)
        );

        assertTrue(exception.getMessage().contains("キャンペーンが存在しません"));
        assertTrue(exception.getMessage().contains("doy0axcfv3grfpt"));
    }

    @Test
    @DisplayName("複数回の取得でも同じ結果が返される")
    void multipleRetrievals() {
        Number number = new Number("dOY0aXcFv3grfpT");

        Campaign result1 = campaignDataSource.findCampaign(number);
        Campaign result2 = campaignDataSource.findCampaign(number);

        assertEquals(result1, result2);
        assertEquals(result1.number().value(), result2.number().value());
        assertNotNull(result1.title());
        assertNotNull(result2.title());
    }

    @Test
    @DisplayName("実施中キャンペーン一覧を複数回取得しても同じ結果が返される")
    void multipleOngoingCampaignsRetrievals() {
        Campaigns result1 = campaignDataSource.findOngoingCampaigns();
        Campaigns result2 = campaignDataSource.findOngoingCampaigns();

        assertEquals(result1.list().size(), result2.list().size());
        assertEquals(result1, result2);
    }

    @Test
    @DisplayName("初期化前の状態でも例外が発生しない")
    void withoutInitialization() {
        CampaignDataSource uninitializedDataSource = new CampaignDataSource();

        // 初期化していない状態でも例外が発生しないことを確認
        assertDoesNotThrow(() -> {
            Campaigns result = uninitializedDataSource.findOngoingCampaigns();
            assertNotNull(result);
        });
    }
}
