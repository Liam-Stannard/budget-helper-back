package com.stannard.liam.transaction.expense;

import com.stannard.liam.user.User;
import com.stannard.liam.user.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {

  @Autowired
  private final ExpenseRepository expenseRepository;

  @Autowired
  private final UserRepository userRepository;

  public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository) {
    this.expenseRepository = expenseRepository;
    this.userRepository = userRepository;
  }

  public List<Expense> getExpenses() {
    return expenseRepository.findAll();
  }

  public Optional<Expense> getExpenseById(Long id) {
    return expenseRepository.findById(id);
  }

  public void insertExpense(Expense expense) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    Optional<User> user = userRepository.findByUsername(username);

    if (user.isEmpty()) {
      throw new IllegalStateException("User  doesn't exist with username: " + username);
    }

    expense.setUser(user.get());
    expenseRepository.save(expense);

  }

  public void updateExpense(Long id, Expense expenseUpdate) {
    expenseRepository.findById(id).map(expense -> {
      expense.setAccount(expenseUpdate.getAccount());
      expense.setDate(expenseUpdate.getDate());
      expense.setTitle(expenseUpdate.getTitle());
      expense.setAmount(expenseUpdate.getAmount());
      expense.setUser(expenseUpdate.getUser());
      expense.setCategory(expenseUpdate.getCategory());
      return expenseRepository.save(expense);
    });

  }

  public void deleteExpense(Long id) {
    if (!expenseRepository.existsById(id)) {
      throw new IllegalStateException("Shopping List doesn't exist with id - [" + id + "]");
    }

    expenseRepository.deleteById(id);
  }
}
