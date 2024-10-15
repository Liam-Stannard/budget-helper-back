package com.stannard.liam.transaction;

import com.stannard.liam.user.User;
import com.stannard.liam.user.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  @Autowired
  private final TransactionRepository transactionRepository;

  @Autowired
  private final UserRepository userRepository;

  public TransactionService(TransactionRepository transactionRepository,
      UserRepository userRepository) {
    this.transactionRepository = transactionRepository;
    this.userRepository = userRepository;
  }

  public List<Transaction> getTransactions() {
    return transactionRepository.findAll();
  }

  public Optional<Transaction> getTransactionById(Long id) {
    return transactionRepository.findById(id);
  }

  public void insertTransaction(Transaction transaction) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    Optional<User> user = userRepository.findByUsername(username);

    if (user.isEmpty()) {
      throw new IllegalStateException("User  doesn't exist with username: " + username);
    }

    transaction.setUser(user.get());
    transactionRepository.save(transaction);

  }

  public void updateTransaction(Long id, Transaction transactionUpdate) {
    transactionRepository.findById(id).map(transaction -> {
      transaction.setAccount(transactionUpdate.getAccount());
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
}
