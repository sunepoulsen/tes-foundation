package dk.sunepoulsen.tes.deployment.core.steps;

import dk.sunepoulsen.tes.flows.FlowStep;
import lombok.Getter;

/** Base deployment step identified by a key. */
@Getter
public abstract class AbstractDeployStep implements FlowStep {
    /** Key used to identify and time the step. */
    private final String key;

    protected AbstractDeployStep(String key) {
        this.key = key;
    }

    /** @return the step key as its timer name */
    @Override
    public String timerName() {
        return key;
    }

}
