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

public class TesContainerStepsFactory extends AbstractContainerStepsFactory {

    public TesContainerStepsFactory(String imageName, String imageTag, SystemUnderTestDeployment systemUnderTestDeployment, SutCreateTestContainerNetworkStep createTestContainerNetworkStep, Path logDirectory) {
        super(imageName, imageTag, systemUnderTestDeployment, createTestContainerNetworkStep, logDirectory);
    }

    public ContainerStepResult<SutStartTesServiceStep> createSteps(List<String> profiles, String serviceKey, CertificateStepsResult certificateStepsResult, ConfigurationFileStepsResult configSteps) {
        SutStartTesServiceStep startFeaturesServiceStep = new SutStartTesServiceStep("startContainer." + serviceKey, serviceKey, new ContainerSecureProtocol(), systemUnderTestDeployment);
        startFeaturesServiceStep.setDockerImageName(new AtomicDataSupplier<>(imageName));
        startFeaturesServiceStep.setDockerImageTag(new AtomicDataSupplier<>(imageTag));
        startFeaturesServiceStep.setNetwork(createTestContainerNetworkStep.getCreatedNetwork());
        startFeaturesServiceStep.getAliases().add(new AtomicDataSupplier<>(serviceKey));
        startFeaturesServiceStep.setProfiles(new AtomicDataSupplier<>(profiles));
        startFeaturesServiceStep.getCertificateFiles().add(certificateStepsResult.getSaveCertificateStep().getCreatedPath());
        startFeaturesServiceStep.getConfigurationFiles().add(configSteps.getSaveConfigurationStep().getCreatedPath());
        startFeaturesServiceStep.setLogPath(new AtomicDataSupplier<>(logDirectory));

        return new ContainerStepResult<>(startFeaturesServiceStep);
    }
}
