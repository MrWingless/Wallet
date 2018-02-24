package com.company;

public class Player {
    private String username;
    private int balanceVersion;
    private double balance;

    public Player(String username, int balanceVersion, double balance) {
        this.username = username;
        this.balanceVersion = balanceVersion;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public int getBalanceVersion() {
        return balanceVersion;
    }

    public double getBalance() {
        return balance;
    }
}
