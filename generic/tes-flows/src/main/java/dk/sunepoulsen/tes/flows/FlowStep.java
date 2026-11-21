package dk.sunepoulsen.tes.flows;

/** A named step that can be executed as part of a flow. */
public interface FlowStep {

    /**
     * Returns the name used for the step's timer.
     *
     * @return the timer name
     */
    String timerName();
    /**
     * Executes the step.
     *
     * @return the step's result
     */
    FlowStepResult execute();
}
