package com.stannard.liam.transaction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.stannard.liam.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@MappedSuperclass
public abstract class Transaction<T> {

  @ManyToOne
  @JsonBackReference
  protected User user;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  @Enumerated(EnumType.STRING)
  @Column(name = "category")
  private T category;

  private String title;
  private String amount;
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private Date date;
  private String account;

  public Transaction() {

  }

  public Transaction(T category, String title, String amount, Date date, String account) {
    this.category = category;
    this.title = title;
    this.amount = amount;
    this.date = date;
    this.account = account;
  }
}
