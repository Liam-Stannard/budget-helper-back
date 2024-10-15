package com.stannard.liam.transaction;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  @NotNull
  @PostFilter("filterObject.user.getId() == principal.id")
  @Override
  List<Transaction> findAll();

}
