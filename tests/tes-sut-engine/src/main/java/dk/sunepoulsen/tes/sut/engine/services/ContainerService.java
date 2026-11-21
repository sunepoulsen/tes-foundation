package dk.sunepoulsen.tes.sut.engine.services;

import dk.sunepoulsen.tes.flows.FlowStep;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.testcontainers.containers.GenericContainer;

import java.util.function.Function;

/** Container-backed service with a configurable undeployment step. */
@Data
@RequiredArgsConstructor
public class ContainerService implements SutService {
    /** Service identifier. */
    private final String key;
    /** Container backing the service. */
    private final GenericContainer<?> container;
    /** Factory for the service's undeployment step. */
    private final Function<SutService, FlowStep> undeployStepFunction;

    @Override
    public String key() {
        return this.key;
    }

    @Override
    public GenericContainer<?> container() {
        return this.container;
    }

    @Override
    public FlowStep undeployStep() {
        return undeployStepFunction.apply(this);
    }
}
