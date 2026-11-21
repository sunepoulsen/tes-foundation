package dk.sunepoulsen.tes.flows;

import java.util.List;

/** Reports the state of flow step executors. */
public interface FlowReport {
    /**
     * Prints a report for the supplied steps.
     *
     * @param steps step executors to report
     */
    void printReport(List<FlowStepExecutor> steps);
}
