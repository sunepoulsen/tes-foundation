package dk.sunepoulsen.tes.sut.engine.steps;

import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier;
import dk.sunepoulsen.tes.deployment.core.steps.AbstractDeployStep;
import dk.sunepoulsen.tes.flows.FlowStepResult;
import lombok.Getter;
import org.testcontainers.containers.Network;

/** Creates a Testcontainers network for the system under test. */
public class SutCreateTestContainerNetworkStep extends AbstractDeployStep {

    /** Supplier populated with the created network on execution. */
    @Getter
    private final AtomicDataSupplier<Network> createdNetwork;

    /**
     * Creates a network step.
     * @param key step identifier
     */
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
