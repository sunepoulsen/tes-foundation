package dk.sunepoulsen.tes.deployment.core.steps.factories;

import dk.sunepoulsen.tes.flows.FlowStep;

import java.util.List;

/** Result exposing the steps produced by a factory. */
public interface AbstractStepsResult {
    /**
     * Returns the steps in execution order.
     * @return the ordered steps
     */
    List<FlowStep> steps();
}
