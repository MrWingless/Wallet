package com.company;

import java.sql.*;

// Writes and reads database entries
public final class DatabaseManager {
    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet result = null;

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

    public static Player getPlayer(String username) throws SQLException {
        Player player;
        try {
            result = statement.executeQuery(
                    "SELECT USERNAME, BALANCE_VERSION, BALANCE FROM PLAYER");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (result.wasNull()) {
            Logger.log("The player Does not exist in our Database. Creating : " + username);
            player = new Player(username, 0, 0);
            Logger.log("New Player created : " + player.toString());
        } else {
            player = new Player(result.getString("USERNAME"), result.getInt("BALANCE_VERSION"), result.getDouble("BALANCE"));
            Logger.log("Found Player : " + player.toString());
        }

        return player;
    }
}
