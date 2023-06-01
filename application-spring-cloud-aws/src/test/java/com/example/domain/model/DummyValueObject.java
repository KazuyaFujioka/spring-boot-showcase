package com.example.domain.model;

class DummyValueObject {
  String value;

  DummyValueObject(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }

  DummyValueObject() {}
}
