package com.example.infrastructure.datasource;

import com.example.application.repository.CampaignRepository;
import com.example.domain.model.Campaign;
import com.example.domain.model.CampaignFactory;
import com.example.domain.model.Campaigns;
import com.example.domain.model.Number;
import com.example.domain.policy.ResourceNotFoundException;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
class CampaignDataSource implements CampaignRepository {

  Set<Campaign> ongoingCampaigns;
  Set<Campaign> campaigns;

  @Override
  public Campaigns findOngoingCampaigns() {
    return new Campaigns(ongoingCampaigns);
  }

  @Override
  public Campaign findCampaign(Number number) {
    Optional<Campaign> campaign =
        campaigns.stream().filter(c -> c.number().equals(number)).findFirst();
    if (campaign.isPresent()) return campaign.get();
    String message = String.format("キャンペーンが存在しません(問い合わせ管理番号:%s)", number);
    throw new ResourceNotFoundException(message);
  }

  @PostConstruct
  void init() {
    Campaign ongoingCampaign1 =
        CampaignFactory.create(
            "ボーナスキャンペーン",
            LocalDateTime.now(),
            LocalDateTime.now().plusMonths(1),
            "0nss8Qp2du2cSWT");

    Campaign ongoingCampaign2 =
        CampaignFactory.create(
            "お友達紹介キャンペーン",
            LocalDateTime.now(),
            LocalDateTime.now().plusYears(1),
            "dOY0aXcFv3grfpT");

    Campaign closedCampaign =
        CampaignFactory.create(
            "終了しているキャンペーン",
            LocalDateTime.of(2023, 2, 1, 0, 0, 0),
            LocalDateTime.of(2023, 2, 28, 23, 59, 59),
            "wJ6PmEWbwSvxKvG");

    ongoingCampaigns = new HashSet<>();
    ongoingCampaigns.add(ongoingCampaign1);
    ongoingCampaigns.add(ongoingCampaign2);

    campaigns = new HashSet<>();
    campaigns.add(closedCampaign);
    campaigns.addAll(ongoingCampaigns);
  }

  CampaignDataSource() {}
}
