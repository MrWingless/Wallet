package com.company;

import java.sql.*;

// Writes and reads database entries
public final class DatabaseManager {
    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;

    public DatabaseManager() {
        try {
            Class.forName("hsqldb.src.org.hsqldb.jdbc.JDBCDriver.java");
            connection = DriverManager.getConnection("jdbc:hsqldb:mem:.", "Wingless", "none");
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Player getPlayer(String username) throws SQLException {
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
        }

        return player;
    }
}
