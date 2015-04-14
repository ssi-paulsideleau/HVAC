package com.surveysampling.apps.hvac.hardware;

public class EnvironmentController {
    private HVAC hvac;

    private static final int HEAT_FAN_COOL_DOWN_PERIOD = 5;
    private static final int COOL_FAN_COOL_DOWN_PERIOD = 3;
    private FanTimer heaterFanTimer;
    private FanTimer coolerFanTimer;

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
        if(hvac.temp() < 65) {
            hvac.heat(true);
            if (heaterFanTimer.canRun()) {
                hvac.fan(true);
            }
        }
    }

    private void turnOnCoolIfTooHot() {
        if(hvac.temp() > 75) {
            hvac.cool(true);
            if (coolerFanTimer.canRun()) {
                hvac.fan(true);
            }
        }
    }

    private void turnOffIfInRange() {
        if(hvac.temp() >= 65 && hvac.temp() <= 75) {
            hvac.heat(false);
            hvac.cool(false);
            hvac.fan(false);
            heaterFanTimer.start();
            coolerFanTimer.start();
        }
    }
}
