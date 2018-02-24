package com.company;

public class JobServer implements Runnable {
    private static final Logger.LogType logType = Logger.LogType.JOB_SERVER;
    private Thread thread;
    private boolean running = true;
    private int sleepTimeMilliseconds = 60000; // 1 minute (5 = 300000)

    public JobServer() {
        Logger.log(logType, "Creating Server");
    }

    public void run() {
        while(running){
            try{
                Thread.sleep(sleepTimeMilliseconds);
            }
            catch (InterruptedException e){
                Logger.log(logType, "Waiting Failure!");
                Logger.log(Logger.LogType.ERROR, "JobServer Failure : " + e.getStackTrace().toString());
            }
            Logger.log(logType, "Triggering Player Data Save Job!");
            Memory.saveData();
        }
    }

    public void start(){
        Logger.log(logType, "Starting Server");
        if (thread == null) {
            thread = new Thread (this);
            thread.start ();
        }
    }
}
