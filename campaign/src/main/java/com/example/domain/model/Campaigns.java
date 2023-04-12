package com.example.domain.model;

import java.util.Set;

/** キャンペーン一覧 */
public class Campaigns {

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
