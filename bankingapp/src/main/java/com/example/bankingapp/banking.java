package com.example.bankingapp;

import java.util.ArrayList;
import java.util.List;

public class banking {
    private double balance;
    private List<Double> withdrawalHistory = new ArrayList<>(); // Stores last transactions


    public banking(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            withdrawalHistory.add(amount); // Store transaction
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            withdrawalHistory.add(-amount); // Store transaction
            return true;
        }
        return false;
    }
    public List<Double> getWithdrawalHistory() {
        return withdrawalHistory; // Return last withdrawals
    }
}
