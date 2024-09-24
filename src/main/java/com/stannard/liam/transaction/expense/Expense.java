package com.stannard.liam.transaction.expense;

import com.stannard.liam.transaction.Transaction;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converts;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "expense")
@Converts({@Convert(attributeName = "category", converter = ExpenseCategoryConverter.class)})
@AttributeOverrides({
    @AttributeOverride(name = "category", column = @Column(name = "category", columnDefinition = "VARCHAR(20)", nullable = false))})
public class Expense extends Transaction<ExpenseCategory> {

  public Expense(String title, String amount, ExpenseCategory category, Date date, String account) {
    super(category, title, amount, date, account);
  }
}
