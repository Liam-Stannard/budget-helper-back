package com.stannard.liam.config;

import com.stannard.liam.transaction.expense.Expense;
import com.stannard.liam.transaction.expense.ExpenseCategory;
import com.stannard.liam.transaction.expense.ExpenseRepository;
import com.stannard.liam.user.Role;
import com.stannard.liam.user.User;
import com.stannard.liam.user.UserService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DevStartup implements CommandLineRunner {

  @Autowired
  private final ExpenseRepository expenseRepository;

  @Autowired
  private final UserService userService;

  @Autowired
  PasswordEncoder passwordEncoder;

  public DevStartup(ExpenseRepository expenseRepository, UserService userService,
      PasswordEncoder passwordEncoder) {
    this.expenseRepository = expenseRepository;
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void run(String... args) throws Exception {
    final String username = "test1";

    User user = User.builder()
        .username(username)
        .role(Role.ROLE_USER)
        .email("test123@test.com")
        .password(passwordEncoder.encode("password1"))
        .build();

    userService.addNewUser(user);

    Expense e1 = new Expense("Meal Out", "100.00", ExpenseCategory.FOOD, new Date(), "Main");
    Expense e2 = new Expense("Electricity", "170.00", ExpenseCategory.BILL, new Date(), "Main");

    e1.setUser(user);
    e2.setUser(user);
    expenseRepository.save(e1);
    expenseRepository.save(e2);


  }

}
