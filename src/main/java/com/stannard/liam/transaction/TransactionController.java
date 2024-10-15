package com.stannard.liam.transaction;

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
@RequestMapping("/api/v1/transaction")

public class TransactionController {

  @Autowired
  private final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @GetMapping("")
  public ResponseEntity<Object> getTransactions() {
    return ResponseEntity.ok().body(new HashMap<>() {{
      put("transactions", transactionService.getTransactions());
    }});
  }

  @PostMapping("")
  public void postTransaction(@RequestBody Transaction transaction) {
    transactionService.insertTransaction(transaction);
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<Object> getTransaction(@PathVariable Long id) {
    return ResponseEntity.ok().body(new HashMap<>() {{
      put("transaction", transactionService.getTransactionById(id));
    }});
  }

  @PutMapping("/id/{id}")
  public void putTransaction(@RequestBody Transaction transaction, @PathVariable Long id) {
    transactionService.updateTransaction(id, transaction);
  }

  @DeleteMapping("/id/{id}")
  public void deleteTransaction(@PathVariable Long id) {
    transactionService.deleteTransaction(id);
  }
}
