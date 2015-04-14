package com.surveysampling.apps.hvac

import com.surveysampling.apps.hvac.hardware.EnvironmentController
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll

class EnvironmentControllerTest extends Specification {
    FakeHVAC fakeHVAC = new FakeHVAC()
    EnvironmentController environmentController = new EnvironmentController(fakeHVAC);

    @Unroll
    def "should do nothing when temp is #currentTemp"() {
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
    def "should turn on heat when temp is #currentTemp <= 64"() {
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
    def "should turn off heat and fan when temp becomes #currentTemp"() {
        given:
        fakeHVAC.currentTemp = currentTemp
        fakeHVAC.heatOn = true
        fakeHVAC.coolOn = false
        fakeHVAC.fanOn = true

        when:
        environmentController.tick();

        then:
        !fakeHVAC.heatOn
        !fakeHVAC.coolOn
        !fakeHVAC.fanOn

        where:
        currentTemp << [65, 70, 75]
    }

    @Unroll
    def "should turn on cool when temp is #currentTemp >= 76"() {
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

    @Unroll
    def "should turn off cool and fan when temp becomes #currentTemp"() {
        given:
        fakeHVAC.currentTemp = currentTemp
        fakeHVAC.heatOn = false
        fakeHVAC.coolOn = true
        fakeHVAC.fanOn = true

        when:
        environmentController.tick();

        then:
        !fakeHVAC.heatOn
        !fakeHVAC.coolOn
        !fakeHVAC.fanOn

        where:
        currentTemp << [65, 70, 75]
    }

    @Unroll
    def "should not turn fan on if fan is on heater cool down"() {
        given:
        fakeHVAC.currentTemp = 64
        environmentController.tick()
        fakeHVAC.currentTemp = 65
        environmentController.tick()
        fakeHVAC.currentTemp = 64

        when:
        ticks.times { environmentController.tick(); }

        then:
        fakeHVAC.fanOn == fanTurnedOn

        where:
        ticks | fanTurnedOn
        0     | false
        1     | false
        2     | false
        3     | false
        4     | false
        5     | true
    }

    def "heater cool down should not affect cooler timer"() {
        when:
        fakeHVAC.currentTemp = 65
        environmentController.tick()
        fakeHVAC.currentTemp = 64

        then:
        environmentController.coolerFanTimer.canRun() == true
    }

    def "cooler cool down should not affect heater timer"() {
        when:
        fakeHVAC.currentTemp = 76
        environmentController.tick()
        fakeHVAC.currentTemp = 75

        then:
        environmentController.heaterFanTimer.canRun() == true
    }

    @Unroll
    def "should turn on heaters fan if coolers fan timer can run after #ticks ticks"() {
        given:
        fakeHVAC.currentTemp = 76
        environmentController.tick()
        fakeHVAC.currentTemp = 75
        environmentController.tick()
        fakeHVAC.currentTemp = 64

        when:
        ticks.times { environmentController.tick(); }

        then:
        fakeHVAC.fanOn == fanTurnedOn

        where:
        ticks | fanTurnedOn
        1     | false
        2     | false
        3     | true
        4     | true
        5     | true
    }

    @Unroll
    def "coolers fan should be #fanTurnedOn if heaters fan timer can run after #ticks ticks"() {
        given:
        fakeHVAC.currentTemp = 64
        environmentController.tick()
        fakeHVAC.currentTemp = 65
        environmentController.tick()
        fakeHVAC.currentTemp = 76

        when:
        ticks.times { environmentController.tick(); }

        then:
        fakeHVAC.fanOn == fanTurnedOn

        where:
        ticks | fanTurnedOn
        1     | false
        2     | false
        3     | false
        4     | false
        5     | true
    }

    @Unroll
    def "should not turn fan on if fan is on cooler cool down"() {
        given:
        fakeHVAC.currentTemp = 76
        environmentController.tick()
        fakeHVAC.currentTemp = 75
        environmentController.tick()
        fakeHVAC.currentTemp = 76

        when:
        ticks.times { environmentController.tick(); }

        then:
        fakeHVAC.fanOn == fanTurnedOn

        where:
        ticks | fanTurnedOn
        0     | false
        1     | false
        2     | false
        3     | true
        4     | true
    }

    def "should not turn fan on after 2 heater on/off cycles"() {
        given:
        forceHeatOn()
        forceHeatOff()
        4.times { environmentController.tick() }
        forceHeatOn()
        forceHeatOff()
        fakeHVAC.currentTemp = 40

        when:
        environmentController.tick()

        then:
        fakeHVAC.fanOn == false
    }

    def "should not turn fan on after 2 cooler on/off cycles"() {
        given:
        forceCoolOn()
        forceCoolOff()
        4.times { environmentController.tick() }
        forceCoolOn()
        forceCoolOff()
        fakeHVAC.currentTemp = 80

        when:
        environmentController.tick()

        then:
        fakeHVAC.fanOn == false
    }

    private void forceHeatOn() {
        fakeHVAC.currentTemp = 40
        environmentController.tick()
        assert fakeHVAC.heatOn == true
    }

    private void forceHeatOff() {
        fakeHVAC.currentTemp = 70
        environmentController.tick()
        assert fakeHVAC.heatOn == false
    }

    private void forceCoolOn() {
        fakeHVAC.currentTemp = 80
        environmentController.tick()
        assert fakeHVAC.coolOn == true
    }

    private void forceCoolOff() {
        fakeHVAC.currentTemp = 70
        environmentController.tick()
        assert fakeHVAC.coolOn == false
    }
}