package com.company;

public class Transaction {
    private static final Logger.LogType logType = Logger.LogType.TRANSACTION;
    private String username;
    private int transactionID;
    private double balanceChange;

    public Transaction(String username, int transactionID, double balanceChange) {
        this.username = username;
        this.transactionID = transactionID;
        this.balanceChange = balanceChange;
    }

    public String getUsername() {
        return username;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public double getBalanceChange() {
        return balanceChange;
    }

    public void make() {
        Logger.log(logType, "Making a Transaction " + this.transactionID + " with Amount : " + this.balanceChange + " to " + this.username);
        Player ourPlayer = Memory.getPlayer(this.username);
        ourPlayer.changeBalance(balanceChange);
    }
}
