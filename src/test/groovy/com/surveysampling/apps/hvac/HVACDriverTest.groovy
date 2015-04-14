package com.surveysampling.apps.hvac

import com.surveysampling.apps.hvac.hardware.EnvironmentController
import spock.lang.Specification

/**
 * command line parameter format
 * gradle run -Pargs=45,55
 */
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
    def "should initialize server when server and port provided"(){
        given:
        int low = 65
        int max = 75
        int port=8080
        when:
        HVACDriver.main([low.toString(), max.toString(), port.toString()] as String[])
        EnvironmentController controller = EnvironmentControllerLocator.environmentController

        then:
        controller
        controller.max == max
        controller.min == low
//        controller.stopServer()

    }

 }

