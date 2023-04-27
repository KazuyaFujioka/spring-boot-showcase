package com.example.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/** キャンペーン */
public record Campaign(
    @JsonProperty Title title, @JsonProperty Period period, @JsonProperty Number number) {}
