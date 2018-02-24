package com.company;

public class WalletServer implements Runnable {
    private static final Logger.LogType logType = Logger.LogType.WALLET_SERVER;
    private Thread thread;

    public WalletServer() {
        Logger.log(logType, "Creating Server");
    }

    public void run() {

    }

    public void start(){
        Logger.log(logType, "Starting Server");
        if (thread == null) {
            thread = new Thread (this);
            thread.start ();
        }
    }
}
