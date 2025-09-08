package dk.sunepoulsen.tes.sut.engine.steps;

import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier;
import dk.sunepoulsen.tes.flows.FlowStepResult;
import dk.sunepoulsen.tes.sut.engine.services.SutService;

import java.nio.file.Path;

public class SutShutdownTesServiceStep extends AbstractShutdownContainerStep {

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
