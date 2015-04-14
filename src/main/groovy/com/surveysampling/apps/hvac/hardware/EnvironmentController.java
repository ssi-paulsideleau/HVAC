package com.surveysampling.apps.hvac.hardware;

import com.paytonrules.SocketWrapper;

public class EnvironmentController {
    private HVAC hvac;

    private static final int HEAT_FAN_COOL_DOWN_PERIOD = 5;
    private static final int COOL_FAN_COOL_DOWN_PERIOD = 3;
    FanTimer heaterFanTimer;
    FanTimer coolerFanTimer;
    private int min = 65;
    private int max = 75;
    private boolean heatOn = false;
    private boolean coolOn = false;

    private SocketWrapper socketWrapper;
    private Thread socketThread;

    public EnvironmentController(HVAC hvac) {
        this.hvac = hvac;
        this.heaterFanTimer = new FanTimer(HEAT_FAN_COOL_DOWN_PERIOD);
        this.coolerFanTimer = new FanTimer(COOL_FAN_COOL_DOWN_PERIOD);
    }

    public void tick() {
        heaterFanTimer.decrementTicks();
        coolerFanTimer.decrementTicks();
        turnOnHeatWhenTooCold();
        turnOnCoolIfTooHot();
        turnOffIfInRange();
    }

    private void turnOnHeatWhenTooCold() {
        if(hvac.temp() < min) {
            hvac.heat(true);
            heatOn = true;
            turnOnFanIfAllowed();
        }
    }

    private void turnOnCoolIfTooHot() {
        if(hvac.temp() > max) {
            hvac.cool(true);
            coolOn = true;
            turnOnFanIfAllowed();
        }
    }

    private void turnOnFanIfAllowed() {
        if (heaterFanTimer.canRun() && coolerFanTimer.canRun()) {
            hvac.fan(true);
        }
    }

    private void turnOffIfInRange() {
        if(hvac.temp() >= min && hvac.temp() <= max) {
            if(heatOn) {
                heatOn = false;
                heaterFanTimer.start();
            }
            if(coolOn) {
                coolOn = false;
                coolerFanTimer.start();
            }
            hvac.heat(false);
            hvac.cool(false);
            hvac.fan(false);
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
