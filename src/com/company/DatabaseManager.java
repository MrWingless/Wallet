package com.company;

import java.sql.*;
import java.text.SimpleDateFormat;

// Writes and reads database entries
public final class DatabaseManager {
    private static final SimpleDateFormat DB_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static final Logger.LogType LOG_TYPE = Logger.LogType.DATABASE;
    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet result = null;

    public DatabaseManager() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            //Class.forName("hsqldb.src.org.hsqldb.jdbc.JDBCDriver.java");
            connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/walletdb", "SA", "");
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Player getPlayer(String username) {
        Player player = null;
        try {
            result = statement.executeQuery("SELECT USERNAME, BALANCE_VERSION, BALANCE FROM PLAYER WHERE USERNAME = '" + username + "'");
            result.next();
            if (result.getRow() < 1) {
                Logger.log(LOG_TYPE, "The player Does not exist in our Database. Creating : " + username);
                player = new Player(username, 0, 0);
                Logger.log(LOG_TYPE, "New Player created : " + player.getUsername() + " Balance : " + player.getBalance() + " BalanceVersion : " + player.getBalanceVersion());
            } else {
                player = new Player(result.getString("USERNAME"), result.getInt("BALANCE_VERSION"), result.getDouble("BALANCE"));
                Logger.log(LOG_TYPE, "Found Player : " + player.getUsername() + " Balance : " + player.getBalance() + " BalanceVersion : " + player.getBalanceVersion());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return player;
    }

    public static Transaction getTransaction(int transactionID) throws SQLException{
        if (transactionExists(transactionID)){
            result = statement.executeQuery("SELECT TRANSACTIONID, USERNAME, ERRORCODE, AMOUNT, DATE, BALANCEBEFORE, BALANCEAFTER, BALANCEVERSION FROM TRANSACTIONHISTORY WHERE TRANSACTIONID = " + transactionID);
            result.next();
            int errorCode = result.getInt("ERRORCODE");
            Transaction tempTransaction = new Transaction(result.getInt("TRANSACTIONID"), result.getString("USERNAME"), (errorCode == 0 ? new ResultSuccess() : new ResultFail(errorCode)), result.getDouble("AMOUNT"),  result.getTimestamp("DATE"), result.getDouble("BALANCEBEFORE"), result.getDouble("BALANCEAFTER"), result.getInt("BALANCEVERSION"));
            return tempTransaction;
        } else {
            return null;
        }
    }

    public static void savePlayer(Player player) throws SQLException {
        if (playerExists(player.getUsername())) {
            statement.executeQuery("UPDATE PLAYER SET BALANCE = " + player.getBalance() + ", BALANCE_VERSION = " + player.getBalanceVersion() + " WHERE USERNAME = '" + player.getUsername() + "'");
            Logger.log(LOG_TYPE, "Player Updated successfully : " + player.getUsername());
        } else {
            statement.executeQuery("INSERT INTO PLAYER (USERNAME, BALANCE_VERSION, BALANCE) VALUES ('" + player.getUsername() + "', " + player.getBalanceVersion() + ", " + player.getBalance() + ")");
            Logger.log(LOG_TYPE, "Player Inserted successfully : " + player.getUsername());
        }
    }

    public static void saveTransaction(Transaction transaction) throws SQLException{
        if (transactionExists(transaction.getTransactionID())){
            Logger.log(Logger.LogType.FAILURE, "Trying to save a Transaction that's already in DB. Should not be possible.");
        } else {
            statement.executeQuery("INSERT INTO TRANSACTIONHISTORY (TRANSACTIONID, USERNAME, ERRORCODE, AMOUNT, DATE, BALANCEBEFORE, BALANCEAFTER, BALANCEVERSION) VALUES ("+transaction.getTransactionID()+", '"+transaction.getUsername()+"', " + transaction.getResult().getCode() + ", " + transaction.getBalanceChangeAmount() + ", '" + DB_DATE_FORMAT.format(transaction.getDateTime()) + "', " + (Double.toString(transaction.getBalanceBefore()) ==  "" ? "NULL" : transaction.getBalanceBefore()) + ", " + transaction.getBalanceAfter() + ", " + transaction.getBalanceVersion() + ")");
            Logger.log(LOG_TYPE, "Transaction "+ transaction.getTransactionID() + " added to Database");
        }
    }

    private static boolean playerExists(String username) throws SQLException {
        boolean answer = false;
        result = statement.executeQuery("SELECT COUNT(*) FROM PLAYER WHERE USERNAME = '" + username + "'");
        result.next();
        if (result.getInt("C1") > 0) {
            answer = true;
        }
        return answer;
    }

    public static boolean transactionExists(int transactionID) throws SQLException{
        boolean answer = false;
        // Checking only the latest 1000 entries
        result = statement.executeQuery("SELECT COUNT(*) FROM (SELECT * FROM TRANSACTIONHISTORY ORDER BY DATE DESC LIMIT 1000) WHERE TRANSACTIONID = " + transactionID);
        result.next();
        if (result.getInt("C1") > 0){
            answer = true;
        }
        return answer;
    }

}
