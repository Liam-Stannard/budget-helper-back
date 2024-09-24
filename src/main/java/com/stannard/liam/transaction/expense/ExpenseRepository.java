package com.stannard.liam.transaction.expense;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

  @NotNull
  @PostFilter("filterObject.user.getId() == principal.id")
  @Override
  List<Expense> findAll();

}
