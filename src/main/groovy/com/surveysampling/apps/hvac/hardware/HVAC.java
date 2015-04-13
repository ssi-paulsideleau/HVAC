package com.surveysampling.apps.hvac.hardware;

public interface HVAC {
    void heat(boolean on);
    void cool(boolean on);
    void fan(boolean on);
    int temp();
}
