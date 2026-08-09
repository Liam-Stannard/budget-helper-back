package com.stannard.liam.transaction;

import com.stannard.liam.account.Account;
import com.stannard.liam.account.AccountRepository;
import com.stannard.liam.exception.ApiRequestException;
import com.stannard.liam.user.User;
import com.stannard.liam.user.UserRepository;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  @Autowired
  private final TransactionRepository transactionRepository;

  @Autowired
  private final UserRepository userRepository;

  @Autowired
  private final AccountRepository accountRepository;

  public TransactionService(TransactionRepository transactionRepository,
      UserRepository userRepository, AccountRepository accountRepository) {
    this.transactionRepository = transactionRepository;
    this.userRepository = userRepository;
    this.accountRepository = accountRepository;
  }

  public List<Transaction> getTransactionList() {
    return transactionRepository.findAll();
  }

  public Optional<Transaction> getTransactionById(Long id) {
    return transactionRepository.findById(id);
  }

  public void insertTransaction(Transaction transaction) {
    User user = getCurrentUser();

    transaction.setUser(user);
    transaction.setAccount(resolveAccountForUser(transaction.getAccount(), user));
    transactionRepository.save(transaction);

  }

  public void updateTransaction(Long id, Transaction transactionUpdate) {
    User user = getCurrentUser();
    Account account = resolveAccountForUser(transactionUpdate.getAccount(), user);

    transactionRepository.findById(id).map(transaction -> {
      transaction.setAccount(account);
      transaction.setDate(transactionUpdate.getDate());
      transaction.setTitle(transactionUpdate.getTitle());
      transaction.setAmount(transactionUpdate.getAmount());
      transaction.setUser(transactionUpdate.getUser());
      transaction.setCategory(transactionUpdate.getCategory());
      return transactionRepository.save(transaction);
    });

  }

  public void deleteTransaction(Long id) {
    if (!transactionRepository.existsById(id)) {
      throw new IllegalStateException("Shopping List doesn't exist with id - [" + id + "]");
    }

    transactionRepository.deleteById(id);
  }

  private User getCurrentUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    Optional<User> user = userRepository.findByUsername(username);

    if (user.isEmpty()) {
      throw new IllegalStateException("User  doesn't exist with username: " + username);
    }

    return user.get();
  }

  private Account resolveAccountForUser(Account requestedAccount, User user) {
    if (requestedAccount == null || requestedAccount.getId() == null) {
      throw new ApiRequestException("Transaction must reference an account", HttpStatus.BAD_REQUEST,
          ZonedDateTime.now());
    }

    Account account = accountRepository.findById(requestedAccount.getId())
        .orElseThrow(() -> new ApiRequestException(
            "Account doesn't exist with id - [" + requestedAccount.getId() + "]",
            HttpStatus.NOT_FOUND, ZonedDateTime.now()));

    if (!account.getUser().getId().equals(user.getId())) {
      throw new ApiRequestException("Account does not belong to the current user",
          HttpStatus.FORBIDDEN, ZonedDateTime.now());
    }

    return account;
  }
}
