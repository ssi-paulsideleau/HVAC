package com.surveysampling.apps.hvac.hardware;

public interface IEnvironmentController {
    void tick();

    void setMin(int min);

    void setMax(int max);
}
