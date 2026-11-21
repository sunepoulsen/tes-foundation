package dk.sunepoulsen.tes.sut.engine.steps.factories;

import dk.sunepoulsen.tes.deployment.core.steps.factories.AbstractStepsResult;
import dk.sunepoulsen.tes.flows.FlowStep;
import dk.sunepoulsen.tes.sut.engine.steps.AbstractSutContainerStep;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/** A factory result containing one container step.
 * @param <T> type of container step
 */
@Getter
@RequiredArgsConstructor
public class ContainerStepResult<T extends AbstractSutContainerStep> implements AbstractStepsResult {

    /** Container step included in the result. */
    private final T step;

    @Override
    public List<FlowStep> steps() {
        return List.of(step);
    }

}
