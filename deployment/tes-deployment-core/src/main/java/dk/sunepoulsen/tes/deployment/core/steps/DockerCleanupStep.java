package dk.sunepoulsen.tes.deployment.core.steps;

import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier;
import dk.sunepoulsen.tes.flows.FlowStepResult;
import dk.sunepoulsen.tes.flows.exceptions.FlowStepException;
import dk.sunepoulsen.tes.io.visitors.DeletePathVisitor;
import dk.sunepoulsen.tes.utils.ProcessExecutor;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Getter
@Setter
public class DockerCleanupStep extends AbstractDeployStep {

    public DockerCleanupStep(String key) {
        super(key);
    }

    @Override
    public FlowStepResult execute() {
        try {
            ProcessExecutor executor = new ProcessExecutor();
            executor.execute("docker", "system", "prune", "-f");

            return FlowStepResult.OK;
        } catch (Exception ex) {
            throw new FlowStepException(ex.getMessage(), ex);
        }
    }

}
