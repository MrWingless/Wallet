package com.company;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Memory {
    private static final Logger.LogType logType = Logger.LogType.MEMORY;
    public static final ConcurrentHashMap<String, Player> playersInMemory = new ConcurrentHashMap<>();

    public boolean playerExistsInMemory(Player p){
        return playersInMemory.containsKey(p.getUsername());
    }

    public Player getPlayer(String username){
        if (playersInMemory.containsKey(username)){
            return playersInMemory.get(username);
        } else {
            return DatabaseManager.getPlayer(username);
        }
    }

    private void saveData(){
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
