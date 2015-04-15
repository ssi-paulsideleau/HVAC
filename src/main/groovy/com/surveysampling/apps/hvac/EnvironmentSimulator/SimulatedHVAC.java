package com.surveysampling.apps.hvac.EnvironmentSimulator;

import com.surveysampling.apps.hvac.hardware.HVAC;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulatedHVAC implements HVAC {
    AtomicInteger temp;
    Thread temperatureChangerThread;

    final AtomicBoolean heatOn = new AtomicBoolean(false);
    final AtomicBoolean coolOn = new AtomicBoolean(false);
    final AtomicBoolean fanOn = new AtomicBoolean(false);

    public SimulatedHVAC() {
        this.temp = new AtomicInteger(55);
        temperatureChangerThread = new TemperatureChangerThread(temp, heatOn, coolOn, fanOn);
        temperatureChangerThread.start();
    }

    @Override
    public void heat(boolean on) {
        heatOn.set(on);
    }

    @Override
    public void cool(boolean on) {
        coolOn.set(on);
    }

    @Override
    public void fan(boolean on) {
        fanOn.set(on);
    }

    @Override
    public int temp() {
        return temp.intValue();
    }

    private static class TemperatureChangerThread extends Thread{
        AtomicInteger temp;
        AtomicBoolean tempRaising = new AtomicBoolean(false);

        AtomicBoolean heatOn = new AtomicBoolean(false);
        AtomicBoolean coolOn = new AtomicBoolean(false);
        AtomicBoolean fanOn = new AtomicBoolean(false);

        TemperatureChangerThread(AtomicInteger temp, AtomicBoolean heatOn, AtomicBoolean coolOn, AtomicBoolean fanOn) {
            this.temp = temp;
            this.heatOn = heatOn;
            this.coolOn = coolOn;
            this.fanOn = fanOn;
        }

        public void run() {
            try {
                while(true) {
                    Thread.sleep(30000);
                    if (heatOn.get() && fanOn.get()) {
                        tempRaising.set(true);
                    } else {
                        tempRaising.set(false);
                    }

                    if (tempRaising.get()) {
                        temp.incrementAndGet();
                    } else {
                        temp.decrementAndGet();
                    }
                    System.out.println("Temp: " + temp +
                            ", Heat On: " + heatOn +
                            ", Cool On: " + coolOn +
                            ", Fan On: " + fanOn);
                }

            }
            catch(InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
