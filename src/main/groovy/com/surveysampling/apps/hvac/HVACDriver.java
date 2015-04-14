package com.surveysampling.apps.hvac;

import com.surveysampling.apps.hvac.hardware.DummyHVAC;
import com.surveysampling.apps.hvac.hardware.EnvironmentController;

import java.util.Arrays;

public class HVACDriver {
    public static void main(String[] args) {
        System.out.println(args[0]);
        System.out.println(args[1]);

        EnvironmentControllerLocator.environmentController = new EnvironmentController(new DummyHVAC());
        EnvironmentControllerLocator.environmentController.setMin(Integer.parseInt(args[0]));
        EnvironmentControllerLocator.environmentController.setMax(Integer.parseInt(args[1]));
    }
}
