package dk.sunepoulsen.tes.sut.engine.steps;

import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier;
import dk.sunepoulsen.tes.flows.FlowStepResult;
import dk.sunepoulsen.tes.sut.engine.services.SutService;

import java.nio.file.Path;

/** Copies a TES service log and stops its container, even if copying fails. */
public class SutShutdownTesServiceStep extends AbstractShutdownContainerStep {

    /**
     * Creates a shutdown step and ensures the log directory exists.
     * @param key step identifier
     * @param logPath log directory supplier
     * @param sutService service to stop
     * @throws dk.sunepoulsen.tes.sut.engine.exceptions.ContainerStepException if the directory cannot be created
     */
    public SutShutdownTesServiceStep(String key, AtomicDataSupplier<Path> logPath, SutService sutService) {
        super(key, logPath, sutService);
    }

    @Override
    public FlowStepResult execute() {
        try {
            sutService.container().copyFileFromContainer("/app/logs/service.log", logFilePath().toString());
            return FlowStepResult.OK;
        }
        finally {
            shutdownContainer();
        }
    }

}
