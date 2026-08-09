package com.stannard.liam.account;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

  @NotNull
  @PostFilter("filterObject.user.getId() == principal.id")
  @Override
  List<Account> findAll();

}
