package com.stannard.liam.config;

import com.stannard.liam.transaction.Transaction;
import com.stannard.liam.transaction.TransactionCategory;
import com.stannard.liam.transaction.TransactionRepository;
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
  private final TransactionRepository transactionRepository;

  @Autowired
  private final UserService userService;

  @Autowired
  PasswordEncoder passwordEncoder;

  public DevStartup(TransactionRepository transactionRepository, UserService userService,
      PasswordEncoder passwordEncoder) {
    this.transactionRepository = transactionRepository;
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

    Transaction e1 = Transaction.builder()
        .category(TransactionCategory.BILL)
        .title("Electric")
        .date(new Date())
        .account("Main")
        .amount("100.00")
        .build();

    Transaction e2 = Transaction.builder()
        .category(TransactionCategory.FOOD)
        .title("Food shop")
        .date(new Date())
        .account("Main")
        .amount("100.00")
        .build();

    e1.setUser(user);
    e2.setUser(user);
    transactionRepository.save(e1);
    transactionRepository.save(e2);


  }

}
