package com.stannard.liam.transaction.expense;

import java.util.HashMap;
import java.util.Optional;
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
@RequestMapping("/api/v1/expense")

public class ExpenseController {

  @Autowired
  private final ExpenseService expenseService;

  public ExpenseController(ExpenseService expenseService) {
    this.expenseService = expenseService;
  }

  @GetMapping("")
  public ResponseEntity<Object> getExpenses() {
    return ResponseEntity.ok().body(new HashMap<>() {{
      put("expenses", expenseService.getExpenses());
    }});
  }

  @PostMapping("")
  public void postExpense(@RequestBody Expense expense) {
    expenseService.insertExpense(expense);
  }

  @GetMapping("/{id}")
  public Optional<Expense> getExpense(@PathVariable Long id) {
    return expenseService.getExpenseById(id);
  }

  @PutMapping("/{id}")
  public void putExpense(@RequestBody Expense expense, @PathVariable Long id) {
    expenseService.updateExpense(id, expense);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    expenseService.deleteExpense(id);
  }
}
