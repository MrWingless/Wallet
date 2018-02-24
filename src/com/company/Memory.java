package com.company;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Memory {
    private static final Logger.LogType logType = Logger.LogType.MEMORY;
    public static final ConcurrentHashMap<String, Player> playersInMemory = new ConcurrentHashMap<>();

    public static boolean playerExistsInMemory(String username){
        return playersInMemory.containsKey(username);
    }

    public static Player getPlayer(String username){
        if (playerExistsInMemory(username)){
            return playersInMemory.get(username);
        } else {
            Player p = DatabaseManager.getPlayer(username);
            playersInMemory.put(username, p);
            return p;
        }
    }

    public static void saveData(){
        Logger.log(logType,"Saving Players from Memory into the Database : Started");
        for (Map.Entry<String, Player> entry : playersInMemory.entrySet()){
            try {
                DatabaseManager.savePlayer(entry.getValue());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        playersInMemory.clear();
        Logger.log(logType, "Saving Players from Memory into the Database : Completed");
    }

}
