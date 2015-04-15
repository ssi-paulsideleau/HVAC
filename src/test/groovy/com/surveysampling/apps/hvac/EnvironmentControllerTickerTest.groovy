package com.surveysampling.apps.hvac

import com.surveysampling.apps.hvac.hardware.EnvironmentController
import com.surveysampling.apps.hvac.hardware.IEnvironmentController
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Timeout
import spock.util.concurrent.PollingConditions

import java.util.concurrent.TimeUnit


class EnvironmentControllerTickerTest extends Specification {
    StubEnvironmentController controller = new StubEnvironmentController()
    @Subject
    EnvironmentControllerTicker ticker = new EnvironmentControllerTicker(100L, controller)

    def cleanup() {
        ticker.stop()
    }

    @Timeout(value = 30, unit=TimeUnit.SECONDS)
    def "should tick every 100 milliseconds"() {
        when:
        ticker.start()
        Thread.sleep(1000)
        ticker.stop()

        then: "it should have at least executed 10 times in a second"
        controller.durations.size() >= 10

        and: "thread sleeping is not precise but something is wrong if it ended up running too often"
        controller.durations.size() <= 20

        and: "time intervals should at least be 100 milliseconds apart"
        for (int i = 1; i < controller.durations.size; i++ ) {
            long diff = controller.durations[i].time - controller.durations[i - 1].time
            diff >= 100
        }
    }

    @Timeout(value = 30, unit=TimeUnit.SECONDS)
    def "should start"() {
        when:
        ticker.start()

        then:
        ticker.started
    }

    @Timeout(value = 30, unit=TimeUnit.SECONDS)
    def "should stop"() {
        given:
        PollingConditions pollingConditions = new PollingConditions(timeout: 2, initialDelay: 1)
        ticker.start()

        when:
        Thread.sleep(100)
        ticker.stop()

        then:
        pollingConditions.eventually {
            ticker.isThreadStopped()
        }

    }


    public static class StubEnvironmentController implements IEnvironmentController {
        def durations = []

        @Override
        void tick() {
            durations.add(new Date())
        }
    }
}
