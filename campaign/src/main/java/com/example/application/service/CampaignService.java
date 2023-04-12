package com.example.application.service;

import com.example.application.repository.CampaignRepository;
import com.example.domain.model.Campaign;
import com.example.domain.model.Campaigns;
import com.example.domain.model.Number;
import org.springframework.stereotype.Service;

@Service
public class CampaignService {

  CampaignRepository campaignRepository;

  public Campaigns findOngoingCampaigns() {
    return campaignRepository.findOngoingCampaigns();
  }

  public Campaign findCampaign(Number number) {
    return campaignRepository.findCampaign(number);
  }

  CampaignService(CampaignRepository campaignRepository) {
    this.campaignRepository = campaignRepository;
  }
}
