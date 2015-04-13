package com.surveysampling.apps.hvac

import com.surveysampling.apps.hvac.hardware.EnvironmentController
import spock.lang.Specification
import spock.lang.Unroll


class EnvironmentControllerTest extends Specification {
    FakeHVAC fakeHVAC = new FakeHVAC()
    EnvironmentController environmentController = new EnvironmentController(fakeHVAC);

    @Unroll
    def "should do nothing"() {
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

    def "should turn on heat when temp is below 65"() {
        given:
        fakeHVAC.currentTemp = 64
        fakeHVAC.heatOn = false
        fakeHVAC.fanOn = false

        when:
        environmentController.tick();

        then:
        fakeHVAC.heatOn
        fakeHVAC.fanOn
    }
}