package dk.sunepoulsen.tes.sut.engine.steps;

import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier;
import dk.sunepoulsen.tes.deployment.core.steps.AbstractDeployStep;
import dk.sunepoulsen.tes.sut.engine.services.SutService;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public abstract class AbstractShutdownContainerStep extends AbstractDeployStep  {

    protected final AtomicDataSupplier<Path> logPath;
    protected final SutService sutService;

    protected AbstractShutdownContainerStep(String key, AtomicDataSupplier<Path> logPath, SutService sutService) {
        super(key);
        this.logPath = logPath;
        this.sutService = sutService;
    }

    protected Path logFilePath() {
        FileSystem fs = FileSystems.getDefault();
        return fs.getPath(logPath.get("Log path has not been set").toAbsolutePath().toString(), sutService.key() + ".log");
    }

    protected void shutdownContainer() {
        this.sutService.container().stop();
    }

}
