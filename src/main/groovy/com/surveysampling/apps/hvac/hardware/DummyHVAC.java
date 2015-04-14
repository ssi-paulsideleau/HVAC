package com.surveysampling.apps.hvac.hardware;

public class DummyHVAC implements HVAC {
    @Override
    public void heat(boolean on) {

    }

    @Override
    public void cool(boolean on) {

    }

    @Override
    public void fan(boolean on) {

    }

    @Override
    public int temp() {
        return 0;
    }
}
