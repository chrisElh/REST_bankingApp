package com.example.bankingapp;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class banking {
    private String accountId;
    private double balance;
    private final Lock lock = new ReentrantLock(); // Lock for thread safety

    public banking(String accountId, double initialBalance) {
        this.accountId = accountId;
        this.balance = initialBalance;
    }

    public String getAccountId() {
        return accountId;
    }

    public double getBalance() {
        // No lock needed since it's a read operation
        return balance;
    }

    public void deposit(double amount) {
        lock.lock(); // Lock to prevent concurrent modifications
        try {
            if (amount > 0) {
                balance += amount;
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean withdraw(double amount) {
        lock.lock();
        try {
            if (amount > 0 && balance >= amount) {
                balance -= amount;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }
}
