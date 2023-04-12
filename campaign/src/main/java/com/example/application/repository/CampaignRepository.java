package com.example.application.repository;

import com.example.domain.model.Campaign;
import com.example.domain.model.Campaigns;
import com.example.domain.model.Number;

public interface CampaignRepository {
  Campaigns findOngoingCampaigns();

  Campaign findCampaign(Number number);
}
