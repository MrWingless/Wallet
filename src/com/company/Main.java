package com.company;

import java.sql.SQLException;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws SQLException {
        int port = 7142;
        Configuration conf = new Configuration();
        DatabaseManager dbm = new DatabaseManager();

        /**
        // TESTING JOB SERVER
        JobServer js = new JobServer();
        js.start();**/

        // TESTING TRANSACTIONS AND DB
        Transaction t1 = new Transaction("James", 100560, 30);
        Transaction t2 = new Transaction("James", 100559, -10);
        Transaction t21 = new Transaction("James", 100562, -8);
        Transaction t22 = new Transaction("James", 100561, -3);
        Transaction t3 = new Transaction("Jane", 100557, 30);
        Transaction t4 = new Transaction("Jane", 100558, -100);

        Result r = t1.make();
        System.out.println(r.code);
        System.out.println(r.errorType.toString());
        /**
        t2.make();
        t3.make();
        t4.make();
        t21.make();
        t22.make();**/

        /**
         // LOGGING TEST :
         System.out.println("haha");
         Date date = new Date();
         String fileName = "LogFile_" + date.toString();
         System.out.println(fileName);

         Logger.log("Hello");
         Logger.log("This is a test");
         Logger.log("How are you?");
         try {
         Thread.sleep(2000);
         } catch (InterruptedException e) {
         e.printStackTrace();
         }
         Logger.log("Hmm... \n yes"); **/

        /**
         // DB TEST :
         DatabaseManager dbM = new DatabaseManager();
         dbM.getPlayer("Peter");
         dbM.getPlayer("Peter");
         dbM.getPlayer("Peter");
         **/

        /**
         // SERVERS TO RUN :
         WalletServer ws = new WalletServer(port);
         ws.start();


         Client c = new Client();
         c.start();
         **/

    }
}
