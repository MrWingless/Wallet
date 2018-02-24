package com.company;

import java.net.Socket;

public class Client implements Runnable{
    private static final Logger.LogType logType = Logger.LogType.CLIENT;
    private Thread thread;
    private Socket socket;

    public Client() {
        Logger.log(logType, "Creating");
    }

    public void run() {

    }

    public void start(){
        Logger.log(logType, "Starting");
        if (thread == null) {
            thread = new Thread (this);
            thread.start ();
        }
    }
}
