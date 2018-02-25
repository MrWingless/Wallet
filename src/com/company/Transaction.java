package com.company;

public class Transaction {
    private static final Logger.LogType LOG_TYPE = Logger.LogType.TRANSACTION;
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

    public Result make() {
        Logger.log(LOG_TYPE, "Making a Transaction " + transactionID + " with Amount : " + balanceChange + " to " + username);
        Player ourPlayer = Memory.getPlayer(username);

        // Checking if the changes is withing configured limits

        // Checking if player has enough money for the transaction
        if ((ourPlayer.getBalance() + balanceChange) < 0) {
            Logger.log(Logger.LogType.ERROR, "Player " + ourPlayer.getUsername() + " does not have enough money for the transaction. Balance : " + ourPlayer.getBalance() + " | Change amount : " + balanceChange);
            return new ResultFail(55, Result.Type.ERROR_NOT_ENOUGH_MONEY);
        } else {
            ourPlayer.changeBalance(balanceChange);
            return new ResultSuccess();
        }
    }
}
