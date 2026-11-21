package dk.sunepoulsen.tes.deployment.core.steps;

import dk.sunepoulsen.tes.flows.FlowStepResult;
import dk.sunepoulsen.tes.flows.exceptions.FlowStepException;
import dk.sunepoulsen.tes.utils.ProcessExecutor;
import lombok.Getter;
import lombok.Setter;

/** Runs Docker system prune without an interactive confirmation. */
@Getter
@Setter
public class DockerCleanupStep extends AbstractDeployStep {

    /**
     * Creates a Docker cleanup step.
     * @param key step and timer key
     */
    public DockerCleanupStep(String key) {
        super(key);
    }

    /**
     * Runs the cleanup command.
     * @return the successful step result
     * @throws FlowStepException if command execution throws an exception
     */
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
