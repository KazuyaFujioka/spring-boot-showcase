package com.example._configuration.gateway;

record Domain(String value) {
  @Override
  public String toString() {
    return value;
  }
}
