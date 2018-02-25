package com.company;

public class Transaction {
    private static final Logger.LogType LOG_TYPE = Logger.LogType.TRANSACTION;
    private String username;
    private int transactionID;
    private double balanceChange;
    private Result result;

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

    public Result make() {
        Logger.log(LOG_TYPE, "Making a Transaction " + transactionID + " with Amount : " + balanceChange + " to " + username);
        Player ourPlayer = Memory.getPlayer(username);

        if (!transactionStaysWithinConfiguredLimits()) {
            return result = new ResultFail(65, Result.Type.ERROR_TRANSACTION_EXCEEDS_LIMITS);
            //return result;
        }

        // Checking if player has enough money for the transaction
        if ((ourPlayer.getBalance() + balanceChange) < 0) {
            Logger.log(Logger.LogType.ERROR, "Player " + ourPlayer.getUsername() + " does not have enough money for the transaction. Balance : " + ourPlayer.getBalance() + " | Change amount : " + balanceChange);
            return result = new ResultFail(55, Result.Type.ERROR_NOT_ENOUGH_MONEY);
            //return result;
        } else {

            // Makes the transaction
            ourPlayer.changeBalance(balanceChange);
            return result = new ResultSuccess();
            //return result;
        }
    }

    private boolean transactionStaysWithinConfiguredLimits() {
        if (balanceChange < 0) {
            if (Configuration.getBalanceChangeLowerLimit() > balanceChange) {
                Logger.log(LOG_TYPE, "Transaction Amount : " + balanceChange + " Exceeds the current limit : " + Configuration.getBalanceChangeLowerLimit());
                return false;
            }
        }
        if (balanceChange > 0) {
            if (Configuration.getBalanceChangeUpperLimit() < balanceChange) {
                Logger.log(LOG_TYPE, "Transaction Amount : " + balanceChange + " Exceeds the current limit : " + Configuration.getBalanceChangeLowerLimit());
                return false;
            }
        }
        return true;
    }
}
