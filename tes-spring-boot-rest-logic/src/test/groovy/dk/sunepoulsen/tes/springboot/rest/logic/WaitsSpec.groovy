package dk.sunepoulsen.tes.springboot.rest.logic

import spock.lang.Specification

import java.time.Duration
import java.util.concurrent.atomic.AtomicInteger

class WaitsSpec extends Specification {

    private AtomicInteger counter

    void setup() {
        counter = new AtomicInteger(5)
    }

    void "Wait for condition that completes"() {
        expect:
            Waits.waitFor(Duration.ofSeconds(2), Duration.ofMillis(5)) {
                counter.getAndDecrement() < 0
            }
    }

    void "Wait for condition that never completes"() {
        expect:
            !Waits.waitFor(Duration.ofMillis(250), Duration.ofMillis(5)) {
                counter.getAndIncrement() < 0
            }
    }
}
