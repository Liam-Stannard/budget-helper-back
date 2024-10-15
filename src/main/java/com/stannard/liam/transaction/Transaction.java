package com.stannard.liam.transaction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.stannard.liam.user.User;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converts;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
@Converts({@Convert(attributeName = "category", converter = TransactionCategoryConverter.class)})
@AttributeOverrides({
    @AttributeOverride(name = "category", column = @Column(name = "category", columnDefinition = "VARCHAR(20)", nullable = false))})
public class Transaction {

  @ManyToOne
  @JsonBackReference
  protected User user;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  @Enumerated(EnumType.STRING)
  @Column(name = "category")
  private TransactionCategory category;

  private String title;
  private String amount;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date date;
  private String account;

  public Transaction(TransactionCategory category, String title, String amount, Date date,
      String account) {
    this.category = category;
    this.title = title;
    this.amount = amount;
    this.date = date;
    this.account = account;
  }
}
