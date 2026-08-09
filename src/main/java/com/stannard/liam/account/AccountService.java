package com.stannard.liam.account;

import com.stannard.liam.transaction.TransactionRepository;
import com.stannard.liam.user.User;
import com.stannard.liam.user.UserRepository;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import com.stannard.liam.exception.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

  @Autowired
  private final AccountRepository accountRepository;

  @Autowired
  private final UserRepository userRepository;

  @Autowired
  private final TransactionRepository transactionRepository;

  public AccountService(AccountRepository accountRepository, UserRepository userRepository,
      TransactionRepository transactionRepository) {
    this.accountRepository = accountRepository;
    this.userRepository = userRepository;
    this.transactionRepository = transactionRepository;
  }

  public List<Account> getAccountList() {
    return accountRepository.findAll();
  }

  public Optional<Account> getAccountById(Long id) {
    return accountRepository.findById(id);
  }

  public void insertAccount(Account account) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    Optional<User> user = userRepository.findByUsername(username);

    if (user.isEmpty()) {
      throw new IllegalStateException("User doesn't exist with username: " + username);
    }

    account.setUser(user.get());
    accountRepository.save(account);
  }

  public void updateAccount(Long id, Account accountUpdate) {
    accountRepository.findById(id).map(account -> {
      account.setName(accountUpdate.getName());
      return accountRepository.save(account);
    });
  }

  public void deleteAccount(Long id) {
    if (!accountRepository.existsById(id)) {
      throw new ApiRequestException("Account doesn't exist with id - [" + id + "]",
          HttpStatus.NOT_FOUND, ZonedDateTime.now());
    }

    if (transactionRepository.existsByAccountId(id)) {
      throw new ApiRequestException(
          "Cannot delete an account that still has transactions. Reassign or delete its transactions first.",
          HttpStatus.CONFLICT, ZonedDateTime.now());
    }

    accountRepository.deleteById(id);
  }
}
