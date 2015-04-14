package com.surveysampling.apps.hvac

import com.surveysampling.apps.hvac.hardware.EnvironmentController
import spock.lang.Specification


class HVACDriverTest extends Specification {

    def "should initialize hvac environment controller"() {
        given:
        int low = 65
        int max = 75

        when:
        HVACDriver.main([low.toString(), max.toString()] as String[])
        EnvironmentController controller = EnvironmentControllerLocator.environmentController

        then:
        controller != null
        controller.min == low
        controller.max == max
    }
}
