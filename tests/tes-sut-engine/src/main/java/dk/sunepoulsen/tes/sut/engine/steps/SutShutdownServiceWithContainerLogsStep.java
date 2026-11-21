package dk.sunepoulsen.tes.sut.engine.steps;

import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier;
import dk.sunepoulsen.tes.flows.FlowStepResult;
import dk.sunepoulsen.tes.flows.exceptions.FlowStepException;
import dk.sunepoulsen.tes.sut.engine.services.SutService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** Saves container logs and stops the container, even if saving fails. */
public class SutShutdownServiceWithContainerLogsStep extends AbstractShutdownContainerStep {

    /**
     * Creates a shutdown step and ensures the log directory exists.
     * @param key step identifier
     * @param logPath log directory supplier
     * @param sutService service to stop
     * @throws dk.sunepoulsen.tes.sut.engine.exceptions.ContainerStepException if the directory cannot be created
     */
    public SutShutdownServiceWithContainerLogsStep(String key, AtomicDataSupplier<Path> logPath, SutService sutService) {
        super(key, logPath, sutService);
    }

    @Override
    public FlowStepResult execute() {
        try {
            Files.writeString(logFilePath(), sutService.container().getLogs());
            return FlowStepResult.OK;
        } catch (IOException ex) {
            throw new FlowStepException("Could not create log file", ex);
        }
        finally {
            shutdownContainer();
        }
    }

}
