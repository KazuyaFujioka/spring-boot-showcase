package com.example.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

/** キャンペーン一覧 */
public record Campaigns(@JsonProperty("campaigns") Set<Campaign> list) {}
