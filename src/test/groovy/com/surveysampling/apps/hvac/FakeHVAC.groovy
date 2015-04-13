package com.surveysampling.apps.hvac

import com.surveysampling.apps.hvac.hardware.HVAC


public class FakeHVAC implements HVAC {


    boolean heatOn;
    boolean coolOn
    boolean fanOn
    int currentTemp;

    @Override
    void heat(boolean on) {
        heatOn = on
    }

    @Override
    void cool(boolean on) {
        coolOn = on
    }

    @Override
    void fan(boolean on) {
        fanOn = on
    }

    @Override
    int temp() {
        return currentTemp
    }
}
