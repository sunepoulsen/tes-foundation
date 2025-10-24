package dk.sunepoulsen.tes.flows

import dk.sunepoulsen.tes.flows.exceptions.FlowStepException
import io.micrometer.core.instrument.Measurement
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Statistic
import io.micrometer.core.instrument.simple.SimpleMeterRegistry
import spock.lang.Specification
import spock.lang.Unroll
import sun.nio.ch.Streams

import java.util.stream.StreamSupport

class FlowStepExecutorSpec extends Specification {

    private MeterRegistry meterRegistry
    private FlowStep step

    void setup() {
        this.meterRegistry = new SimpleMeterRegistry()
        this.step = Mock(FlowStep)
    }

    @Unroll
    void "Tests executing a step: #_testcase"() {
        given:
            FlowStepExecutor executor = new FlowStepExecutor(meterRegistry.timer('step'), step)

        when:
            FlowStepResult result = executor.execute()
            List<Measurement> measurements = StreamSupport.stream(executor.timer.measure().spliterator(), false).toList()

        then:
            result == _stepResult
            executor.flowStepResult == result
            executor.status == FlowStepExecutorStatus.FINISHED
            measurements.find { it.statistic == Statistic.COUNT }.value == 1.0d
            measurements.find { it.statistic == Statistic.TOTAL_TIME }.value < 1.0d
            measurements.find { it.statistic == Statistic.MAX }.value ==
                measurements.find { it.statistic == Statistic.TOTAL_TIME }.value

            1 * step.execute() >> _stepResult
            step.timerName() >> 'step'
            0 * _

        where:
            _testcase                           | _stepResult
            'Step returns FlowStepResult.OK'    | FlowStepResult.OK
            'Step returns FlowStepResult.ERROR' | FlowStepResult.ERROR
    }

    void "Tests executing a step: Step throws FlowStepException"() {
        given:
            FlowStepExecutor executor = new FlowStepExecutor(meterRegistry.timer('step'), step)

        when:
            executor.execute()

        then:
            thrown(FlowStepException)

            executor.flowStepResult == FlowStepResult.ERROR
            executor.status == FlowStepExecutorStatus.FINISHED

            1 * step.execute() >> {
                throw new FlowStepException('message')
            }
            step.timerName() >> 'step'
            0 * _
    }
}
