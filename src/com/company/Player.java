package com.company;

public class Player {
    private static final Logger.LogType LOG_TYPE = Logger.LogType.PLAYER;
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

    public void changeBalance(double change){
        double oldBalance = balance;
        balance = balance + change;
        balanceVersion++;
        Logger.log(LOG_TYPE, "" + username + " Balance changed from : " + oldBalance + " to " + balance + " :: BalanceVersion " + balanceVersion);
    }
}
