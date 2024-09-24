package com.stannard.liam.transaction.expense;

public enum ExpenseCategory {
  FOOD("Food"), BILL("Bill"), SUBSCRIPTION("Subscription"), CLOTHING("Clothing");

  private final String name;

  ExpenseCategory(String name) {
    this.name = name;
  }

  public String toString() {
    return this.name;
  }
}
