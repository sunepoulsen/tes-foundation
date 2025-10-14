package dk.sunepoulsen.tes.sut.engine.services;

import dk.sunepoulsen.tes.flows.FlowStep;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.testcontainers.containers.GenericContainer;

import java.util.function.Function;

@Data
@RequiredArgsConstructor
public class ContainerService implements SutService {
    private final String key;
    private final GenericContainer<?> container;
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
