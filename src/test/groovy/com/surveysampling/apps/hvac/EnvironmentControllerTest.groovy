package com.surveysampling.apps.hvac

import com.surveysampling.apps.hvac.hardware.EnvironmentController
import spock.lang.Specification
import spock.lang.Unroll

class EnvironmentControllerTest extends Specification {
    FakeHVAC fakeHVAC = new FakeHVAC()
    EnvironmentController environmentController = new EnvironmentController(fakeHVAC);

    @Unroll
    def "should do nothing when temp is *currentTemp"() {
        given:
        fakeHVAC.currentTemp = currentTemp

        when:
        environmentController.tick();

        then:
        !fakeHVAC.heatOn
        !fakeHVAC.coolOn
        !fakeHVAC.fanOn

        where:
        currentTemp << (65..75)
    }

    @Unroll
    def "should turn on heat when temp is *currentTemp <= 64"() {
        given:
        fakeHVAC.currentTemp = currentTemp
        fakeHVAC.heatOn = false
        fakeHVAC.coolOn = false
        fakeHVAC.fanOn = false

        when:
        environmentController.tick();

        then:
        fakeHVAC.heatOn
        !fakeHVAC.coolOn
        fakeHVAC.fanOn

        where:
        currentTemp << [45, 55, 64]
    }

    @Unroll
    def "should turn on cool when temp is *currentTemp >= 76"() {
        given:
        fakeHVAC.currentTemp = currentTemp
        fakeHVAC.heatOn = false
        fakeHVAC.coolOn = false
        fakeHVAC.fanOn = false

        when:
        environmentController.tick();

        then:
        !fakeHVAC.heatOn
        fakeHVAC.coolOn
        fakeHVAC.fanOn

        where:
        currentTemp << [76, 85, 94]
    }
}