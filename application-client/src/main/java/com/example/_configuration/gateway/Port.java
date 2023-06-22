package com.example._configuration.gateway;

record Port(Integer value) {
  @Override
  public String toString() {
    return value.toString();
  }
}
