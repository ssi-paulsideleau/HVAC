package com.surveysampling.apps.hvac

import com.surveysampling.apps.hvac.hardware.HVAC


public class FakeHVAC implements HVAC {

    boolean heatCalled;
    boolean coolCalled;
    boolean fanCalled;
    boolean heatOn;
    int currentTemp;

    @Override
    void heat(boolean on) {
        heatCalled = true
        heatOn = on
    }

    @Override
    void cool(boolean on) {
        coolCalled = true
    }

    @Override
    void fan(boolean on) {
        fanCalled = true
    }

    @Override
    int temp() {
        return currentTemp
    }
}
