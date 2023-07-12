package com.example;

record S3ObjectKey(String value) {
  @Override
  public String toString() {
    return value;
  }
}
