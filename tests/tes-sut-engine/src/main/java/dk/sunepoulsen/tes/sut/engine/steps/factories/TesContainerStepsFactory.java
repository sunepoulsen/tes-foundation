package dk.sunepoulsen.tes.sut.engine.steps.factories;

import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier;
import dk.sunepoulsen.tes.deployment.core.steps.factories.CertificateStepsResult;
import dk.sunepoulsen.tes.deployment.core.steps.factories.ConfigurationFileStepsResult;
import dk.sunepoulsen.tes.sut.engine.services.ContainerSecureProtocol;
import dk.sunepoulsen.tes.sut.engine.steps.SutCreateTestContainerNetworkStep;
import dk.sunepoulsen.tes.sut.engine.steps.SutStartTesServiceStep;
import dk.sunepoulsen.tes.sut.engine.system.SystemUnderTestDeployment;

import java.nio.file.Path;
import java.util.List;

/** Creates configured TES service container steps. */
public class TesContainerStepsFactory extends AbstractContainerStepsFactory {

    /**
     * Creates a TES container step factory.
     * @param imageName Docker image name
     * @param imageTag Docker image tag
     * @param systemUnderTestDeployment deployment receiving services
     * @param createTestContainerNetworkStep step supplying the network
     * @param logDirectory directory for service logs
     */
    public TesContainerStepsFactory(String imageName, String imageTag, SystemUnderTestDeployment systemUnderTestDeployment, SutCreateTestContainerNetworkStep createTestContainerNetworkStep, Path logDirectory) {
        super(imageName, imageTag, systemUnderTestDeployment, createTestContainerNetworkStep, logDirectory);
    }

    /**
     * Creates a start step configured with HTTPS, network, profiles and generated files.
     * @param profiles Spring profiles to activate
     * @param serviceKey service identifier and network alias
     * @param certificateStepsResult result supplying the certificate file
     * @param configSteps result supplying the configuration file
     * @return result containing the configured start step
     */
    public ContainerStepResult<SutStartTesServiceStep> createSteps(List<String> profiles, String serviceKey, CertificateStepsResult certificateStepsResult, ConfigurationFileStepsResult configSteps) {
        SutStartTesServiceStep startTesServiceStep = new SutStartTesServiceStep("startContainer." + serviceKey, serviceKey, new ContainerSecureProtocol(), systemUnderTestDeployment);
        startTesServiceStep.setDockerImageName(new AtomicDataSupplier<>(imageName));
        startTesServiceStep.setDockerImageTag(new AtomicDataSupplier<>(imageTag));
        startTesServiceStep.setNetwork(createTestContainerNetworkStep.getCreatedNetwork());
        startTesServiceStep.getAliases().add(new AtomicDataSupplier<>(serviceKey));
        startTesServiceStep.setProfiles(new AtomicDataSupplier<>(profiles));
        startTesServiceStep.getCertificateFiles().add(certificateStepsResult.getSaveCertificateStep().getCreatedPath());
        startTesServiceStep.getConfigurationFiles().add(configSteps.getSaveConfigurationStep().getCreatedPath());
        startTesServiceStep.setLogPath(new AtomicDataSupplier<>(logDirectory));

        return new ContainerStepResult<>(startTesServiceStep);
    }
}
