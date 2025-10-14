package dk.sunepoulsen.tes.sut.engine.steps;

import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier;
import dk.sunepoulsen.tes.deployment.core.steps.AbstractDeployStep;
import dk.sunepoulsen.tes.flows.FlowStepResult;
import lombok.Getter;
import org.testcontainers.containers.Network;

public class SutCreateTestContainerNetworkStep extends AbstractDeployStep {

    @Getter
    private final AtomicDataSupplier<Network> createdNetwork;

    public SutCreateTestContainerNetworkStep(final String key) {
        super(key);
        this.createdNetwork = new AtomicDataSupplier<>();
    }

    @Override
    public FlowStepResult execute() {
        this.createdNetwork.set(Network.builder()
            .build()
        );

        return FlowStepResult.OK;
    }

}
