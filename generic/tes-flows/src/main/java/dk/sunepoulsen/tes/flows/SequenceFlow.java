package dk.sunepoulsen.tes.flows;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/** Executes flow steps in sequence and ignores subsequent steps after an error. */
@Slf4j
public class SequenceFlow implements Flow {
    /** Name used for logging and as the prefix of step timers. */
    private final String timerName;
    /** Step executors in execution order. */
    private final List<FlowStepExecutor> steps;

    /**
     * Creates a sequential flow with a timer for each step.
     *
     * @param meterRegistry registry used to obtain step timers
     * @param timerName prefix for step timer names
     * @param steps steps in execution order
     */
    public SequenceFlow(final MeterRegistry meterRegistry, final String timerName, List<FlowStep> steps) {
        this.timerName = timerName;
        this.steps = steps.stream()
                .map(flowStep -> new FlowStepExecutor(meterRegistry.timer(timerName + "." + flowStep.timerName()), flowStep))
                .toList();
    }

    /**
     * Executes non-ignored steps in order. An error result causes later steps to be ignored;
     * an exception also causes later steps to be ignored and is rethrown.
     */
    @Override
    public void execute() {
        log.info("Executing flow '{}'", timerName);

        for (FlowStepExecutor step : steps) {
            if (step.getStatus().equals(FlowStepExecutorStatus.IGNORED)) {
                log.info("Skipping step {} because it has been marked as {}", step.getFlowStep().timerName(), step.getStatus());
                continue;
            }

            try {
                if (step.execute() == FlowStepResult.ERROR) {
                    log.debug("Marking remaining steps as IGNORED, because step '{}' failed", step.getFlowStep().timerName());
                    ignoredRemainingStepsAfterError(step);
                }
            } catch (Exception ex) {
                log.debug("Marking remaining steps as IGNORED, because step '{}' throws exception", step.getFlowStep().timerName());
                ignoredRemainingStepsAfterError(step);
                throw ex;
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void printReport() {
        new LogFlowReport().printReport(steps);
    }

    private void ignoredRemainingStepsAfterError(FlowStepExecutor failedStep) {
        int indexFailedStep = steps.indexOf(failedStep);
        if (indexFailedStep == steps.size() - 1) {
            return;
        }

        steps.subList(indexFailedStep + 1, steps.size()).forEach(FlowStepExecutor::ignoreStep);
    }
}
