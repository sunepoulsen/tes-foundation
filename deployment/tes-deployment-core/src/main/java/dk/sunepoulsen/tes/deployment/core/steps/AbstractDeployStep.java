package dk.sunepoulsen.tes.deployment.core.steps;

import dk.sunepoulsen.tes.flows.FlowStep;
import lombok.Getter;

@Getter
public abstract class AbstractDeployStep implements FlowStep {
    private final String key;

    protected AbstractDeployStep(String key) {
        this.key = key;
    }

    @Override
    public String timerName() {
        return key;
    }

}
