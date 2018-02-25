package com.company;

public class JobServer implements Runnable {
    private static final Logger.LogType LOG_TYPE = Logger.LogType.JOB_SERVER;
    private Thread thread;
    private boolean running = true;
    private int sleepTimeMilliseconds = 6000; // 1 minute (5 = 300000)

    public JobServer() {
        Logger.log(LOG_TYPE, "Creating Server");
    }

    public void run() {
        while(running){
            try{
                Thread.sleep(sleepTimeMilliseconds);
            }
            catch (InterruptedException e){
                Logger.log(LOG_TYPE, "Waiting Failure!");
                Logger.log(Logger.LogType.ERROR, "JobServer Failure : " + e.getStackTrace().toString());
            }
            Logger.log(LOG_TYPE, "Triggering Player Data Save Job!");
            Memory.saveData();
            Logger.log(LOG_TYPE, "Player Data Save Job Completed!");
            running = false; // TODO : Remove this line
        }
    }

    public void start(){
        Logger.log(LOG_TYPE, "Starting Server");
        if (thread == null) {
            thread = new Thread (this);
            thread.start ();
        }
    }
}
