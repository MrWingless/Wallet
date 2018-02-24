package com.company;

import java.sql.*;

// Writes and reads database entries
public final class DatabaseManager {
    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet result = null;
    private static final Logger.LogType logType = Logger.LogType.DATABASE;

    public DatabaseManager() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            //Class.forName("com.company.org.hsqldb.jdbc.JDBCDriver"); //hsqldb.src.org.hsqldb.jdbc.JDBCDriver.java");
            connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/walletdb", "SA", "");
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Player getPlayer(String username) {
        Player player = null;
        try {
            result = statement.executeQuery("SELECT USERNAME, BALANCE_VERSION, BALANCE FROM PLAYER");

            if (result.wasNull()) {
                Logger.log(logType, "The player Does not exist in our Database. Creating : " + username);
                player = new Player(username, 0, 0);
                Logger.log(logType, "New Player created : " + player.toString());
            } else {
                player = new Player(result.getString("USERNAME"), result.getInt("BALANCE_VERSION"), result.getDouble("BALANCE"));
                Logger.log(logType, "Found Player : " + player.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return player;
    }

    public static void savePlayer(Player player) throws SQLException {
        if (playerExists(player)) {
            statement.executeQuery("UPDATE PLAYER SET BALANCE = " + player.getBalance() + ", BALANCE_VERSION = " + player.getBalanceVersion() + " WHERE USERNAME = '" + player.getUsername() + "'");
            Logger.log(logType, "Database : Player Updated successfully : " + player.getUsername());
        } else {
            statement.executeQuery("INSERT INTO PLAYER (USERNAME, BALANCE_VERSION, BALANCE) VALUES (" + player.getUsername() + ", " + player.getBalanceVersion() + ", " + player.getBalance() + ")");
            Logger.log(logType, "Database : Player Inserted successfully : " + player.getUsername());
        }
    }

    private static boolean playerExists(Player player) throws SQLException {
        boolean answer = false;
        result = statement.executeQuery("SELECT COUNT(*) FROM PLAYER WHERE USERNAME = '" + player.getUsername() + "'");
        if (result.getInt("COUNT(*)") > 0) {
            answer = true;
        }
        return answer;
    }
}
