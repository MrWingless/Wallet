package com.company;

import java.net.Socket;

public class Client implements Runnable{
    private static final Logger.LogType LOG_TYPE = Logger.LogType.CLIENT;
    private Thread thread;
    private Socket socket;

    public Client() {
        Logger.log(LOG_TYPE, "Creating");
    }

    public void run() {

    }

    public void start(){
        Logger.log(LOG_TYPE, "Starting");
        if (thread == null) {
            thread = new Thread (this);
            thread.start ();
        }
    }
}
