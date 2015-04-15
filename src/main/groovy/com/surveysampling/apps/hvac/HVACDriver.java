package com.surveysampling.apps.hvac;

import com.paytonrules.SocketWrapper;
import com.surveysampling.apps.hvac.hardware.DummyHVAC;
import com.surveysampling.apps.hvac.hardware.EnvironmentController;

public class HVACDriver {

    private SocketWrapper socketWrapper;
    public static void main(String[] args) {

        EnvironmentControllerLocator.environmentController = new EnvironmentController(new DummyHVAC());
        if(args.length>=2) {
            System.out.println("setting min="+args[0]+", max="+Integer.parseInt(args[1]));
            EnvironmentControllerLocator.environmentController.setMin(Integer.parseInt(args[0]));
            EnvironmentControllerLocator.environmentController.setMax(Integer.parseInt(args[1]));
        }
        if(args.length==3){
            System.out.println("activating socket at port "+args[2]);
            EnvironmentControllerLocator.environmentController.setServerPortAndStart(Integer.parseInt(args[2]));
        }

//        EnvironmentControllerLocator.environmentController.stopServer();
        EnvironmentControllerTicker ticker = new EnvironmentControllerTicker(60000, EnvironmentControllerLocator.environmentController);
        EnvironmentControllerLocator.ticker = ticker;

        ticker.start();
        ticker.join();
    }
}
