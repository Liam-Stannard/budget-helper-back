package com.stannard.liam.account;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account")

public class AccountController {

  @Autowired
  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping("")
  public ResponseEntity<Object> getAccountList() {
    return ResponseEntity.ok().body(new HashMap<>() {{
      put("accountList", accountService.getAccountList());
    }});
  }

  @PostMapping("")
  public void postAccount(@RequestBody Account account) {
    accountService.insertAccount(account);
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<Object> getAccount(@PathVariable Long id) {
    return ResponseEntity.ok().body(new HashMap<>() {{
      put("account", accountService.getAccountById(id));
    }});
  }

  @PutMapping("/id/{id}")
  public void putAccount(@RequestBody Account account, @PathVariable Long id) {
    accountService.updateAccount(id, account);
  }

  @DeleteMapping("/id/{id}")
  public void deleteAccount(@PathVariable Long id) {
    accountService.deleteAccount(id);
  }
}
