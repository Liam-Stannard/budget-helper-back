package com.stannard.liam.transaction;

public enum TransactionCategory {
  FOOD("Food"), BILL("Bill"), SUBSCRIPTION("Subscription"), CLOTHING("Clothing");

  private final String name;

  TransactionCategory(String name) {
    this.name = name;
  }

  public String toString() {
    return this.name;
  }
}
