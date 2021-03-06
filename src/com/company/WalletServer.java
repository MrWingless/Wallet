package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WalletServer implements Runnable {
    private static final Logger.LogType LOG_TYPE = Logger.LogType.WALLET_SERVER;
    private Thread thread = null;
    private ServerSocket serverSocket = null;
    private int port = 8080;
    private boolean stop = false;

    public WalletServer(int port) {
        this.port = port;
        Logger.log(LOG_TYPE, "Creating Server");
    }

    public void run() {
        synchronized (this) {
            this.thread = Thread.currentThread();
        }

        int workerCounter = 0;

        openServerSocket();

        while (!this.stop) {
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                Logger.log(LOG_TYPE, "Failed to Accept - Server Scoket");
                Logger.log(Logger.LogType.ERROR, e.getStackTrace().toString());
            }

            new Thread(new ServerWorker(clientSocket, "Worker_" + ++workerCounter)).start();
        }


        /**
         // Creating Sockets
         ServerSocket serverSocket = null;
         try {
         serverSocket = new ServerSocket(port);
         } catch (IOException e){
         Logger.log(LOG_TYPE, "Failed to Listen to port " + port);
         Logger.log(Logger.LogType.ERROR, e.getStackTrace().toString());
         }

         Socket clientSocket = null;
         try {
         clientSocket = serverSocket.accept();
         } catch (IOException e){
         Logger.log(LOG_TYPE, "Failed to Accept server socket");
         Logger.log(Logger.LogType.ERROR, e.getStackTrace().toString());
         }


         try {
         PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
         BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
         String inputLine, outputLine;


         } catch (IOException e){
         Logger.log(LOG_TYPE, "Failed to initialize writers and readers");
         Logger.log(Logger.LogType.ERROR, e.getStackTrace().toString());
         }

         while (true){
         }**/

    }

    public void start() {
        Logger.log(LOG_TYPE, "Starting Server");
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    private synchronized boolean hasStopped() {
        return this.stop;
    }

    public synchronized void stopServer() {
        this.stop = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            Logger.log(LOG_TYPE, "Failed to Close server socket");
            Logger.log(Logger.LogType.ERROR, e.getStackTrace().toString());
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            Logger.log(LOG_TYPE, "Failed to Open server socket");
            Logger.log(Logger.LogType.ERROR, e.getStackTrace().toString());
        }
    }
}
