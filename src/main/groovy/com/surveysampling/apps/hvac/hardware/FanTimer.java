package com.surveysampling.apps.hvac.hardware;

public class FanTimer {
    int ticks;
    boolean started = false;

    FanTimer(int duration) {
        ticks = duration;
    }

    public void start() {
        started = true;
    }

    public void decrementTicks() {
        if (started){
            ticks--;
        }
    }

    public boolean canRun() {
        return ticks <= 0 || !started;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }
}
