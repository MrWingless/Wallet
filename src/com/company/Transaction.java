package com.company;

import java.sql.SQLException;
import java.util.Date;

public class Transaction {
    private static final Logger.LogType LOG_TYPE = Logger.LogType.TRANSACTION;
    private int transactionID;
    private String username;
    private Result result;
    private double balanceChangeAmount;
    private Date dateTime;
    private double balanceBefore;
    private double balanceAfter;
    private double balanceVersion;

    public Transaction(String username, int transactionID, double balanceChangeAmount) {
        this.username = username;
        this.transactionID = transactionID;
        this.balanceChangeAmount = balanceChangeAmount;
    }

    public Transaction(int transactionID, String username, Result result, double balanceChangeAmount, Date dateTime, double balanceBefore, double balanceAfter, double balanceVersion) {
        this.transactionID = transactionID;
        this.username = username;
        this.result = result;
        this.balanceChangeAmount = balanceChangeAmount;
        this.dateTime = dateTime;
        this.balanceBefore = balanceBefore;
        this.balanceAfter = balanceAfter;
        this.balanceVersion = balanceVersion;
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

    public Result make() {
        Player ourPlayer = Memory.getPlayer(username);

        if (transactionAlreadyExists()){
            Logger.log(LOG_TYPE, "Transaction " + transactionID + " already exists with Amount : " + balanceChangeAmount + " to " + username + " : ErrorCode : " + result.getCode());
            return result;
        }
        Logger.log(LOG_TYPE, "Making a Transaction " + transactionID + " with Amount : " + balanceChangeAmount + " to " + username);

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
        Memory.addTransactionToMemory(this);
        return result;
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

    private boolean transactionAlreadyExists(){
        boolean transactionDoesNotExist = false;

        if (Memory.transactionExistsInMemory(transactionID)){
            replaceThisTransaction(Memory.getTransaction(transactionID));
            return true;
        }

        try {
            if (DatabaseManager.transactionExists(transactionID)){
                replaceThisTransaction(DatabaseManager.getTransaction(transactionID));
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return transactionDoesNotExist;
    }

    private void replaceThisTransaction(Transaction newTransaction){
        username = newTransaction.getUsername();
        result = newTransaction.getResult();
        balanceChangeAmount = newTransaction.getBalanceChangeAmount();
        dateTime = newTransaction.getDateTime();
        balanceBefore = newTransaction.getBalanceBefore();
        balanceAfter = newTransaction.getBalanceAfter();
        balanceVersion = newTransaction.getBalanceVersion();
    }
}
