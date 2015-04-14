package com.surveysampling.apps.hvac.hardware;

public class EnvironmentController {
    private HVAC hvac;

    private int tickMinutesSinceHeatFanOff;
    private int tickMinutesSinceCoolFanOff;

    public EnvironmentController(HVAC hvac) {
        this.hvac = hvac;
    }

    public void tick() {
        tickMinutesSinceHeatFanOff++;
        tickMinutesSinceCoolFanOff++;
        turnOnHeatWhenTooCold();
        turnOnCoolIfTooHot();
        turnOffIfInRange();
    }

    private void turnOnHeatWhenTooCold() {
        if(hvac.temp() < 65) {
            hvac.heat(true);
            hvac.fan(true);
        }
    }

    private void turnOnCoolIfTooHot() {
        if(hvac.temp() > 75) {
            hvac.cool(true);
            hvac.fan(true);
        }
    }

    private void turnOffIfInRange() {
        if(hvac.temp() >= 65 && hvac.temp() <= 75) {
            hvac.heat(false);
            hvac.cool(false);
            hvac.fan(false);
            tickMinutesSinceHeatFanOff = 0;
            tickMinutesSinceCoolFanOff = 0;
        }
    }

    public int getTickMinutesSinceHeatFanOff() {
        return tickMinutesSinceHeatFanOff;
    }

    public void setTickMinutesSinceHeatFanOff(int tickMinutesSinceHeatFanOff) {
        this.tickMinutesSinceHeatFanOff = tickMinutesSinceHeatFanOff;
    }

    public int getTickMinutesSinceCoolFanOff() {
        return tickMinutesSinceCoolFanOff;
    }

    public void setTickMinutesSinceCoolFanOff(int tickMinutesSinceCoolFanOff) {
        this.tickMinutesSinceCoolFanOff = tickMinutesSinceCoolFanOff;
    }
}
