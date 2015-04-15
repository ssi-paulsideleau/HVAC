package com.paytonrules;

import com.surveysampling.apps.hvac.hardware.CommandProcessor;

public class FakeCommandProcessor implements CommandProcessor
{
    private volatile String message;
    @Override
    public String process(String value) {
        System.out.println("processing "+value);
        this.message = value;
        return "fake";
    }
    public String getMessage(){
        return message;
    }
}
