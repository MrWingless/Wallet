package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerWorker implements Runnable {
    private static final Logger.LogType LOG_TYPE = Logger.LogType.WALLET_SERVER_WORKER;
    private Socket clientSocket = null;
    private String workerString = null;

    public ServerWorker(Socket clientSocket, String text) {
        this.clientSocket = clientSocket;
        this.workerString = text;
    }

    public void run() {
        try {
            long timeMillis = System.currentTimeMillis();
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();
            long timeMillis2 = timeMillis - System.currentTimeMillis();
            Logger.log(LOG_TYPE, "" + this.workerString + " : Worked " + timeMillis2 + " ms");

            //outputStream.write(("Text to respond with").getBytes());
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
