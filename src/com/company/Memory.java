package com.company;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Memory {
    private static final Logger.LogType LOG_TYPE = Logger.LogType.MEMORY;
    public static final ConcurrentHashMap<String, Player> PLAYERS_IN_MEMORY = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<Integer, Transaction> TRANSACTION_HISTORY = new ConcurrentHashMap<>();

    private static boolean playerExistsInMemory(String username){
        return PLAYERS_IN_MEMORY.containsKey(username);
    }

    private static boolean transactionExistsInMemory(int transactionID){
        return TRANSACTION_HISTORY.containsKey(transactionID);
    }

    public static Player getPlayer(String username){
        if (playerExistsInMemory(username)){
            return PLAYERS_IN_MEMORY.get(username);
        } else {
            Player p = DatabaseManager.getPlayer(username);
            PLAYERS_IN_MEMORY.put(username, p);
            return p;
        }
    }

    public static void saveData(){
        savePlayerData();
        saveTransactionHistory();
    }

    private static void savePlayerData(){
        Logger.log(LOG_TYPE,"Saving Players from Memory into the Database : Started");
        for (Map.Entry<String, Player> entry : PLAYERS_IN_MEMORY.entrySet()){
            try {
                DatabaseManager.savePlayer(entry.getValue());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        PLAYERS_IN_MEMORY.clear();
        Logger.log(LOG_TYPE, "Saving Players from Memory into the Database : Completed");
    }

    private static void saveTransactionHistory(){
        Logger.log(LOG_TYPE,"Saving Transaction History from Memory into the Database : Started");
        for (Map.Entry<Integer, Transaction> entry : TRANSACTION_HISTORY.entrySet()){
            try {
                DatabaseManager.saveTransaction(entry.getValue());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        TRANSACTION_HISTORY.clear();
        Logger.log(LOG_TYPE, "Saving Transaction History from Memory into the Database : Completed");
    }

}
