package com.example.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

/** キャンペーン一覧 */
public class Campaigns {

  @JsonProperty("campaigns")
  Set<Campaign> list;

  public Campaigns(Set<Campaign> list) {
    this.list = list;
  }

  @Override
  public String toString() {
    return "Campaigns{" + "list=" + list + '}';
  }

  Campaigns() {}
}
