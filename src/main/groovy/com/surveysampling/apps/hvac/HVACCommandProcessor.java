package com.surveysampling.apps.hvac;

import com.surveysampling.apps.hvac.hardware.CommandProcessor;
import com.surveysampling.apps.hvac.hardware.IEnvironmentController;

import java.util.Arrays;
import java.util.List;

public class HVACCommandProcessor implements CommandProcessor {
    private IEnvironmentController controller;
    private int lastMax;
    private int lastMin;

    public HVACCommandProcessor(IEnvironmentController controller) {
        this.controller = controller;
    }

    public int getLastMax() {
        return lastMax;
    }

    public int getLastMin() {
        return lastMin;
    }

    @Override
    public String process(String value) {
        if (getCommand(value)) {
            return "OK";
        }

        return "Invalid command";
    }

    private boolean getCommand(String value) {
        int min;
        int max;

        List<String> commands = Arrays.asList(value.split("-"));
        System.out.println(commands);

        if ( commands.size() != 3) return false;
        if (!commands.get(0).equals("")) return false;

        min = getValue(commands.get(1), "min");
        max = getValue(commands.get(2), "max");
        lastMin = min;
        lastMax = max;

        if (min < 0 || max < 0) {
            return false;
        }

        controller.setMin(min);
        controller.setMax(max);
        return true;
    }

    /**
     *
     * @param command e.g. "min 75"
     * @return value e.g. 75
     */
    private int getValue(String command, String expectedPrefix) {
        if (command.startsWith(expectedPrefix+" ")) {
            String textValue = command.split(" ")[1];
            return Integer.parseInt(textValue);
        }
        else {
            return -1;
        }
    }
}
