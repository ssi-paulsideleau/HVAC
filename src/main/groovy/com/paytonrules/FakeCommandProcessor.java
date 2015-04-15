package com.paytonrules;

import com.surveysampling.apps.hvac.hardware.CommandProcessor;

public class FakeCommandProcessor implements CommandProcessor
{
    private volatile String message;
    @Override
    public void process(String value) {
        System.out.println("processing "+value);
        this.message = value;
    }
    public String getMessage(){
        return message;
    }
}
