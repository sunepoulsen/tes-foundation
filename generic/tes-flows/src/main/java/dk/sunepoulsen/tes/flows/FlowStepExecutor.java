package dk.sunepoulsen.tes.flows;

import dk.sunepoulsen.tes.flows.exceptions.FlowStepException;
import io.micrometer.core.instrument.Measurement;
import io.micrometer.core.instrument.Timer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class FlowStepExecutor {
    @Getter
    private final Timer timer;

    @Getter
    private final FlowStep flowStep;

    @Getter
    private FlowStepResult flowStepResult;

    @Getter
    private FlowStepExecutorStatus status;

    FlowStepExecutor(final Timer timer, final FlowStep flowStep) {
        this.timer = timer;
        this.flowStep = flowStep;
        this.flowStepResult = null;
        this.status = FlowStepExecutorStatus.READY;
    }

    FlowStepResult execute() {
        try {
            log.info("Executing step '{}'", flowStep.timerName());

            this.status = FlowStepExecutorStatus.RUNNING;
            this.flowStepResult = timer.record(flowStep::execute);

            return this.flowStepResult;
        } catch (FlowStepException ex) {
            log.debug("Execution step '{}' failed: {}", flowStep.timerName(), ex.getMessage());
            this.flowStepResult = FlowStepResult.ERROR;
            throw ex;
        } finally {
            this.status = FlowStepExecutorStatus.FINISHED;
        }
    }

    void ignoreStep() {
        this.status = FlowStepExecutorStatus.IGNORED;
    }
}
