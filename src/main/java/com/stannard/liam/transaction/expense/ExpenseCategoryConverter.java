package com.stannard.liam.transaction.expense;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ExpenseCategoryConverter implements AttributeConverter<ExpenseCategory, String> {

  @Override
  public String convertToDatabaseColumn(ExpenseCategory attribute) {
    return attribute.toString();
  }

  @Override
  public ExpenseCategory convertToEntityAttribute(String dbData) {
    return ExpenseCategory.valueOf(dbData);
  }
}
