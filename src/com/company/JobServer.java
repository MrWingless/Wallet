package com.company;

public class JobServer implements Runnable {
    private static final Logger.LogType logType = Logger.LogType.JOB_SERVER;
    private Thread thread;

    public JobServer() {
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
