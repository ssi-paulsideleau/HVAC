package com.surveysampling.apps.hvac.hardware;

public class EnvironmentController {
    private HVAC hvac;

    private static final int HEAT_FAN_COOL_DOWN_PERIOD = 5;
    private static final int COOL_FAN_COOL_DOWN_PERIOD = 3;
    private FanTimer heaterFanTimer;
    private FanTimer coolerFanTimer;
    private int min = 65;
    private int max = 75;


    public EnvironmentController(HVAC hvac) {
        this.hvac = hvac;
        this.heaterFanTimer = new FanTimer(HEAT_FAN_COOL_DOWN_PERIOD);
        this.coolerFanTimer = new FanTimer(COOL_FAN_COOL_DOWN_PERIOD);
    }

    public void tick() {
        turnOnHeatWhenTooCold();
        turnOnCoolIfTooHot();
        turnOffIfInRange();
        heaterFanTimer.decrementTicks();
        coolerFanTimer.decrementTicks();
    }

    private void turnOnHeatWhenTooCold() {
        if(hvac.temp() < min) {
            hvac.heat(true);
            if (heaterFanTimer.canRun()) {
                hvac.fan(true);
            }
        }
    }

    private void turnOnCoolIfTooHot() {
        if(hvac.temp() > max) {
            hvac.cool(true);
            if (coolerFanTimer.canRun()) {
                hvac.fan(true);
            }
        }
    }

    private void turnOffIfInRange() {
        if(hvac.temp() >= min && hvac.temp() <= max) {
            hvac.heat(false);
            hvac.cool(false);
            hvac.fan(false);
            heaterFanTimer.start();
            coolerFanTimer.start();
        }
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
