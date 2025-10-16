package dk.sunepoulsen.tes.flows

import dk.sunepoulsen.tes.flows.exceptions.FlowStepException
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.simple.SimpleMeterRegistry
import spock.lang.Specification

class SequenceFlowSpec extends Specification {

    private MeterRegistry meterRegistry

    void setup() {
        this.meterRegistry = new SimpleMeterRegistry()
    }

    void 'Tests flow with an empty set of steps'() {
        given:
            SequenceFlow flow = new SequenceFlow(meterRegistry, 'flow', [])

        when:
            flow.execute()
            flow.printReport()

        then:
            meterRegistry.meters.empty
    }

    void 'Tests flow with one successful step'() {
        given:
            FlowStep step = Mock(FlowStep)
            SequenceFlow flow = new SequenceFlow(meterRegistry, 'flow', [step])

        when:
            flow.execute()
            flow.printReport()

        then:
            step.timerName() >> 'step'
            1 * step.execute() >> FlowStepResult.OK
            0 * _
    }

    void 'Tests flow with one failed step'() {
        given:
            FlowStep step = Mock(FlowStep)
            SequenceFlow flow = new SequenceFlow(meterRegistry, 'flow', [step])

        when:
            flow.execute()
            flow.printReport()

        then:
            step.timerName() >> 'step'
            1 * step.execute() >> FlowStepResult.ERROR
            0 * _
    }

    void 'Tests flow with 5 successful steps'() {
        given:
            List<FlowStep> steps = (1..5).collect {Mock(FlowStep) }
            SequenceFlow flow = new SequenceFlow(meterRegistry, 'flow', steps)

        when:
            flow.execute()
            flow.printReport()

        then:
            (1..steps.size()).collect {
                steps[it-1].timerName() >> "step${it}"
                1 * steps[it-1].execute() >> FlowStepResult.OK
            }
            0 * _
    }

    void 'Tests flow with 5 steps and step 3 fails'() {
        given:
            List<FlowStep> steps = (1..5).collect {Mock(FlowStep) }
            SequenceFlow flow = new SequenceFlow(meterRegistry, 'flow', steps)

        when:
            flow.execute()
            flow.printReport()

        then:
            (1..steps.size()).collect {
                steps[it-1].timerName() >> "step${it}"
            }
            (1..2).collect {
                1 * steps[it-1].execute() >> FlowStepResult.OK
            }
            1 * steps[2].execute() >> FlowStepResult.ERROR
            0 * _
    }

    void 'Tests flow with 5 steps and step 3 throws FlowStepException'() {
        given:
            List<FlowStep> steps = (1..5).collect {Mock(FlowStep) }
            SequenceFlow flow = new SequenceFlow(meterRegistry, 'flow', steps)

        when:
            flow.execute()
            flow.printReport()

        then:
            thrown(FlowStepException)

            (1..steps.size()).collect {
                steps[it-1].timerName() >> "step${it}"
            }
            (1..2).collect {
                1 * steps[it-1].execute() >> FlowStepResult.OK
            }
            1 * steps[2].execute() >> {
                throw new FlowStepException('message')
            }
            0 * _
    }

}
