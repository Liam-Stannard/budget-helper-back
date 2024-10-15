package com.stannard.liam.transaction;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class TransactionCategoryConverter implements
    AttributeConverter<TransactionCategory, String> {

  @Override
  public String convertToDatabaseColumn(TransactionCategory attribute) {
    return attribute.toString();
  }

  @Override
  public TransactionCategory convertToEntityAttribute(String dbData) {
    return TransactionCategory.valueOf(dbData);
  }
}
