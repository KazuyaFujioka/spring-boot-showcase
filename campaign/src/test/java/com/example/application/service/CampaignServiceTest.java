package com.example.application.service;

import com.example.application.repository.CampaignRepository;
import com.example.domain.model.Campaign;
import com.example.domain.model.CampaignFactory;
import com.example.domain.model.Campaigns;
import com.example.domain.model.Number;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("キャンペーンサービスのテストケース")
class CampaignServiceTest {

    @Mock
    private CampaignRepository campaignRepository;

    @InjectMocks
    private CampaignService campaignService;

    @Test
    @DisplayName("実施中キャンペーン一覧を取得できる")
    void findOngoingCampaigns() {
        // Given
        Set<Campaign> campaignSet = Set.of(
            CampaignFactory.create("テスト1", LocalDateTime.now().minusDays(1), 
                                 LocalDateTime.now().plusDays(1), "TEST001"),
            CampaignFactory.create("テスト2", LocalDateTime.now().minusDays(2), 
                                 LocalDateTime.now().plusDays(2), "TEST002")
        );
        Campaigns expectedCampaigns = new Campaigns(campaignSet);
        when(campaignRepository.findOngoingCampaigns()).thenReturn(expectedCampaigns);

        // When
        Campaigns result = campaignService.findOngoingCampaigns();

        // Then
        assertEquals(expectedCampaigns, result);
        assertEquals(2, result.list().size());
        verify(campaignRepository, times(1)).findOngoingCampaigns();
    }

    @Test
    @DisplayName("実施中キャンペーンが存在しない場合は空のリストを取得できる")
    void findOngoingCampaignsEmpty() {
        // Given
        Campaigns emptyCampaigns = new Campaigns(Set.of());
        when(campaignRepository.findOngoingCampaigns()).thenReturn(emptyCampaigns);

        // When
        Campaigns result = campaignService.findOngoingCampaigns();

        // Then
        assertEquals(emptyCampaigns, result);
        assertTrue(result.list().isEmpty());
        verify(campaignRepository, times(1)).findOngoingCampaigns();
    }

    @Test
    @DisplayName("指定した管理番号のキャンペーンを取得できる")
    void findCampaign() {
        // Given
        Number number = new Number("TEST001");
        Campaign expectedCampaign = CampaignFactory.create("テスト", 
            LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1), "TEST001");
        when(campaignRepository.findCampaign(number)).thenReturn(expectedCampaign);

        // When
        Campaign result = campaignService.findCampaign(number);

        // Then
        assertEquals(expectedCampaign, result);
        assertEquals("TEST001", result.number().value());
        assertNotNull(result.title());
        assertNotNull(result.period());
        verify(campaignRepository, times(1)).findCampaign(number);
    }

    @Test
    @DisplayName("存在しない管理番号でキャンペーンを取得しようとした場合の動作")
    void findNonExistentCampaign() {
        // Given
        Number number = new Number("NONEXISTENT");
        when(campaignRepository.findCampaign(number)).thenThrow(new RuntimeException("キャンペーンが存在しません"));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> campaignService.findCampaign(number));

        assertEquals("キャンペーンが存在しません", exception.getMessage());
        verify(campaignRepository, times(1)).findCampaign(number);
    }

    @Test
    @DisplayName("null管理番号でキャンペーンを取得しようとした場合の動作")
    void findCampaignWithNullNumber() {
        // Given
        Number nullNumber = new Number(null);
        when(campaignRepository.findCampaign(nullNumber)).thenReturn(null);

        // When
        Campaign result = campaignService.findCampaign(nullNumber);

        // Then
        assertNull(result);
        verify(campaignRepository, times(1)).findCampaign(nullNumber);
    }

    @Test
    @DisplayName("リポジトリが複数回呼ばれても正しく動作する")
    void multipleRepositoryCalls() {
        // Given
        Number number1 = new Number("TEST001");
        Number number2 = new Number("TEST002");
        Campaign campaign1 = CampaignFactory.create("テスト1", 
            LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1), "TEST001");
        Campaign campaign2 = CampaignFactory.create("テスト2", 
            LocalDateTime.now().minusDays(2), LocalDateTime.now().plusDays(2), "TEST002");

        when(campaignRepository.findCampaign(number1)).thenReturn(campaign1);
        when(campaignRepository.findCampaign(number2)).thenReturn(campaign2);

        // When
        Campaign result1 = campaignService.findCampaign(number1);
        Campaign result2 = campaignService.findCampaign(number2);

        // Then
        assertEquals(campaign1, result1);
        assertEquals(campaign2, result2);
        verify(campaignRepository, times(1)).findCampaign(number1);
        verify(campaignRepository, times(1)).findCampaign(number2);
    }

    @Test
    @DisplayName("実際のデータソースで使用される管理番号でキャンペーンを取得できる")
    void findCampaignWithRealWorldNumber() {
        // Given
        Number number = new Number("dOY0aXcFv3grfpT");
        Campaign expectedCampaign = CampaignFactory.create("お友達紹介キャンペーン", 
            LocalDateTime.of(2023, 1, 1, 0, 0), 
            LocalDateTime.of(2023, 12, 31, 23, 59, 59, 999000000), 
            "dOY0aXcFv3grfpT");
        when(campaignRepository.findCampaign(number)).thenReturn(expectedCampaign);

        // When
        Campaign result = campaignService.findCampaign(number);

        // Then
        assertEquals(expectedCampaign, result);
        assertEquals("dOY0aXcFv3grfpT", result.number().value());
        assertNotNull(result.title());
        assertNotNull(result.period());
        verify(campaignRepository, times(1)).findCampaign(number);
    }
}
