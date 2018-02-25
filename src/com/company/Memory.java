package com.company;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Memory {
    private static final Logger.LogType LOG_TYPE = Logger.LogType.MEMORY;
    public static final ConcurrentHashMap<String, Player> PLAYERS_IN_MEMORY = new ConcurrentHashMap<>();

    public static boolean playerExistsInMemory(String username){
        return PLAYERS_IN_MEMORY.containsKey(username);
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

}
