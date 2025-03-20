package com.example.bankingapp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("/account")
public class bankingController {
    private final Map<String, banking> accounts = new HashMap<>();
    private final Lock lock = new ReentrantLock(); // Global lock for account creation

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestParam String id, @RequestParam double initialBalance) {
        lock.lock();
        try {
            if (accounts.containsKey(id)) {
                return ResponseEntity.badRequest().body("Account with ID " + id + " already exists.");
            }
            accounts.put(id, new banking(id, initialBalance));
            return ResponseEntity.ok("Account created with ID: " + id + " and balance: " + initialBalance);
        } finally {
            lock.unlock();
        }
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<Object> getBalance(@PathVariable String id) {
        banking account = accounts.get(id);
        if (account == null) {
            return ResponseEntity.badRequest().body("Account not found.");
        }
        return ResponseEntity.ok(account.getBalance());
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<String> deposit(@PathVariable String id, @RequestParam double amount) {
        banking account = accounts.get(id);
        if (account == null) {
            return ResponseEntity.badRequest().body("Account not found.");
        }
        account.deposit(amount);
        return ResponseEntity.ok("Deposit successful. New balance: " + account.getBalance());
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable String id, @RequestParam double amount) {
        banking account = accounts.get(id);
        if (account == null) {
            return ResponseEntity.badRequest().body("Account not found.");
        }
        if (account.withdraw(amount)) {
            return ResponseEntity.ok("Withdrawal successful. New balance: " + account.getBalance());
        }
        return ResponseEntity.badRequest().body("Insufficient funds.");
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllAccounts() {
        return ResponseEntity.ok(accounts);
    }
}
