package com.surveysampling.apps.hvac.hardware;

public class EnvironmentController {

    private HVAC hvac;

    public EnvironmentController(HVAC hvac) {
        this.hvac = hvac;
    }

    public void tick() {
        if(hvac.temp() < 65) {
            hvac.heat(true);
        }
    }
}
