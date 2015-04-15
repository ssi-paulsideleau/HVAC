package com.surveysampling.apps.hvac

import spock.lang.Specification
import spock.lang.Unroll

class HVACCommandProcessorTest extends Specification {
    FakeEnvironmentController controller = new FakeEnvironmentController()
    def uut = new HVACCommandProcessor(controller)

    def "should reject garbage message"() {
        expect:
        uut.process("bad input") =~ "Invalid command"
    }
    def "should reject first token bad"() {
        expect:
        uut.process("-minn 50 -max 756") =~ "Invalid command"
    }
    def "should reject second token bad"() {
        expect:
        uut.process("-min 50 -xyz 756") =~ "Invalid command"
    }

    //TODO: test non integer

    @Unroll
    def "should accept good message"() {
        when:
        def message = "-min $min -max $max"

        then:
        uut.process(message) == "OK"
        uut.lastMin == min
        uut.lastMax == max
        controller.getMin() == min
        controller.getMax() == max

        where:
        min|max
        50|75
        50|76
    }
}
