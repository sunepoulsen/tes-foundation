package dk.sunepoulsen.tes.sut.engine.steps.factories;

import dk.sunepoulsen.tes.sut.engine.steps.SutCreateTestContainerNetworkStep;
import dk.sunepoulsen.tes.sut.engine.system.SystemUnderTestDeployment;

import java.nio.file.Path;

/** Shared container step factory configuration. */
public class AbstractContainerStepsFactory {
    protected final String imageName;
    protected final String imageTag;
    protected final SystemUnderTestDeployment systemUnderTestDeployment;
    protected final SutCreateTestContainerNetworkStep createTestContainerNetworkStep;
    protected final Path logDirectory;

    /**
     * Creates a factory with shared container configuration.
     * @param imageName Docker image name
     * @param imageTag Docker image tag
     * @param systemUnderTestDeployment deployment receiving services
     * @param createTestContainerNetworkStep step supplying the network
     * @param logDirectory directory for service logs
     */
    public AbstractContainerStepsFactory(String imageName, String imageTag, SystemUnderTestDeployment systemUnderTestDeployment, SutCreateTestContainerNetworkStep createTestContainerNetworkStep, Path logDirectory) {
        this.imageName = imageName;
        this.imageTag = imageTag;
        this.systemUnderTestDeployment = systemUnderTestDeployment;
        this.createTestContainerNetworkStep = createTestContainerNetworkStep;
        this.logDirectory = logDirectory;
    }
}
