package com.example.endpoint;

import com.example._configuration.openapi.response.NotFound;
import com.example._configuration.openapi.response.Success;
import com.example._configuration.openapi.response.Unknown;
import com.example.application.service.CampaignService;
import com.example.domain.model.Campaign;
import com.example.domain.model.Campaigns;
import com.example.domain.model.Number;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/campaign")
@Tag(name = "campaign", description = "キャンペーン")
class CampaignController {

  CampaignService campaignService;

  @Operation(
      operationId = "findOngoingCampaign",
      summary = "実施中のキャンペーン一覧を取得",
      description = "現在実施中のキャンペーン一覧を取得する",
      responses = {
        @ApiResponse(
            responseCode = Success.code,
            description = Success.description,
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Campaigns.class))),
        @ApiResponse(
            responseCode = NotFound.code,
            description = "実施中のキャンペーンが見つからない",
            content = @Content),
        @ApiResponse(
            responseCode = Unknown.code,
            description = Unknown.description,
            content = @Content)
      })
  @GetMapping("ongoing")
  Campaigns findOngoingCampaign() {
    return campaignService.findOngoingCampaigns();
  }

  @Operation(
      operationId = "findCampaign",
      summary = "キャンペーンを取得",
      description = "キャンペーンを取得する",
      parameters = {@Parameter(name = "number", description = "管理番号", required = true)},
      responses = {
        @ApiResponse(
            responseCode = Success.code,
            description = Success.description,
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Campaign.class))),
        @ApiResponse(
            responseCode = NotFound.code,
            description = "キャンペーンが見つからない",
            content = @Content),
        @ApiResponse(
            responseCode = Unknown.code,
            description = Unknown.description,
            content = @Content)
      })
  @GetMapping("{number}")
  Campaign findCampaign(@PathVariable("number") Number number) {
    return campaignService.findCampaign(number);
  }

  CampaignController(CampaignService campaignService) {
    this.campaignService = campaignService;
  }
}
