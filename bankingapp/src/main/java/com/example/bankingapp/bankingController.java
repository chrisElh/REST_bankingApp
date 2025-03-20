package com.example.bankingapp;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController  // Marks this as a REST API controller
@RequestMapping("/account")  // Base URL: /account
public class bankingController {
    private banking account = new banking(1000.0); // Account with initial balance: 1000

    // Endpoint: Get balance
    @GetMapping("/balance")
    public double getBalance() {
        return account.getBalance();
    }

    // Endpoint: Deposit money
    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestParam double amount) {
        account.deposit(amount);
        return ResponseEntity.ok("Amount deposited successfully. New balance: " + account.getBalance());
    }

    // Endpoint: Withdraw money
    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestParam double amount) {
        if (account.withdraw(amount)) {
            return ResponseEntity.ok("Money withdrew successfully. New balance: " + account.getBalance());
        } else {
            return ResponseEntity.badRequest().body("Withdrawal failed. Insufficient funds or invalid amount.");
        }
    }

    @GetMapping("/withdrawals")
    public ResponseEntity<List<Double>> getWithdrawals() {
        return ResponseEntity.ok(account.getWithdrawalHistory());
    }


}
