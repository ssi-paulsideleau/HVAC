package com.surveysampling.apps.hvac;

import com.surveysampling.apps.hvac.hardware.EnvironmentController;

public class EnvironmentControllerLocator {
    public static volatile EnvironmentController environmentController;
    public static volatile EnvironmentControllerTicker ticker;
}
