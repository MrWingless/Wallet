package com.company;

import java.util.Date;

public class Main {

    public static void main(String[] args) {

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
        Logger.log("Hmm... \n yes");

    }
}
