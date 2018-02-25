package com.company;

import java.util.Date;

public class Transaction {
    private static final Logger.LogType LOG_TYPE = Logger.LogType.TRANSACTION;
    private int transactionID;
    private String username;
    private Result result;
    private double balanceChangeAmount;
    private Date dateTime;
    protected double balanceBefore;
    private double balanceAfter;
    private double balanceVersion;

    public Transaction(String username, int transactionID, double balanceChangeAmount) {
        this.username = username;
        this.transactionID = transactionID;
        this.balanceChangeAmount = balanceChangeAmount;
    }

    public String getUsername() {
        return username;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public double getBalanceChangeAmount() {
        return balanceChangeAmount;
    }

    public Result getResult() {
        return result;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public double getBalanceBefore() {
        return balanceBefore;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public double getBalanceVersion() {
        return balanceVersion;
    }

    public void make() {
        Logger.log(LOG_TYPE, "Making a Transaction " + transactionID + " with Amount : " + balanceChangeAmount + " to " + username);
        Player ourPlayer = Memory.getPlayer(username);

        if (!transactionStaysWithinConfiguredLimits()) {
            result = new ResultFail(65, Result.Type.ERROR_TRANSACTION_EXCEEDS_LIMITS);
        }

        // Checking if player has enough money for the transaction
        if (result == null) {
            if ((ourPlayer.getBalance() + balanceChangeAmount) < 0) {
                Logger.log(Logger.LogType.ERROR, "Player " + ourPlayer.getUsername() + " does not have enough money for the transaction. Balance : " + ourPlayer.getBalance() + " | Change amount : " + balanceChangeAmount);
                result = new ResultFail(55, Result.Type.ERROR_NOT_ENOUGH_MONEY);
            } else {

                // Makes the transaction
                balanceBefore = ourPlayer.changeBalance(balanceChangeAmount);
                result = new ResultSuccess();
            }
        }

        // Saves Transaction History into Memory
        balanceAfter = ourPlayer.getBalance();
        balanceVersion = ourPlayer.getBalanceVersion();
        dateTime = new Date();
        Memory.TRANSACTION_HISTORY.put(transactionID, this);
    }

    private boolean transactionStaysWithinConfiguredLimits() {
        if (balanceChangeAmount < 0) {
            if (Configuration.getBalanceChangeLowerLimit() > balanceChangeAmount) {
                Logger.log(LOG_TYPE, "Transaction Amount : " + balanceChangeAmount + " Exceeds the current limit : " + Configuration.getBalanceChangeLowerLimit());
                return false;
            }
        }
        if (balanceChangeAmount > 0) {
            if (Configuration.getBalanceChangeUpperLimit() < balanceChangeAmount) {
                Logger.log(LOG_TYPE, "Transaction Amount : " + balanceChangeAmount + " Exceeds the current limit : " + Configuration.getBalanceChangeLowerLimit());
                return false;
            }
        }
        return true;
    }
}
