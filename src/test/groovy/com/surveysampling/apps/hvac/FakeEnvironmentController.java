package com.surveysampling.apps.hvac;

import com.surveysampling.apps.hvac.hardware.IEnvironmentController;

public class FakeEnvironmentController implements IEnvironmentController {
    private int min;
    private int max;

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    @Override
    public void tick() {

    }
}
