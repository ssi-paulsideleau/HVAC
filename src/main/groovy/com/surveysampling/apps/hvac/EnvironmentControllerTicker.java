package com.surveysampling.apps.hvac;

import com.surveysampling.apps.hvac.hardware.IEnvironmentController;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

public class EnvironmentControllerTicker {
    private final long sleepTimeInMilliseconds;
    private final IEnvironmentController controller;
    private final AtomicBoolean stop = new AtomicBoolean(false);
    private boolean started = false;
    private final Thread thread;


    public EnvironmentControllerTicker(long sleepTimeInMilliseconds, IEnvironmentController controller) {
        this.sleepTimeInMilliseconds = sleepTimeInMilliseconds;
        this.controller = controller;
        this.thread = new Thread() {
            @Override
            public void run() {
                while (stop.get() == false) {
                    try {
                        System.out.println("ticking at " + new Date());
                        controller.tick();
                        Thread.sleep(sleepTimeInMilliseconds);
                    }
                    catch (InterruptedException ie) {
                        stop.set(true);
                        System.out.println("Interrupted");
                    }
                }
                System.out.println("Stopped");
            }
        };
    }

    public void start() {
        started = true;
        thread.start();
    }

    public void join() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean isThreadStopped() {
        return !thread.isAlive();
    }

    public boolean isStarted() {
        return started;
    }

    public void stop() {
        try {
            started = false;
            stop.set(true);
            thread.interrupt();
            thread.join(10000);
        }
        catch (InterruptedException ie) {
            throw new RuntimeException(ie);
        }
    }
}
