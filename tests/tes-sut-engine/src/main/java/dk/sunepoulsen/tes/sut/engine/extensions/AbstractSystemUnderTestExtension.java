package dk.sunepoulsen.tes.sut.engine.extensions;

import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier;
import dk.sunepoulsen.tes.deployment.core.steps.DeleteDirectoryStep;
import dk.sunepoulsen.tes.deployment.core.steps.factories.CertificateStepsFactory;
import dk.sunepoulsen.tes.deployment.core.steps.factories.CertificateStepsResult;
import dk.sunepoulsen.tes.flows.FlowStep;
import dk.sunepoulsen.tes.flows.SequenceFlow;
import dk.sunepoulsen.tes.sut.engine.services.SutCertificate;
import dk.sunepoulsen.tes.sut.engine.steps.SutCreateTestContainerNetworkStep;
import dk.sunepoulsen.tes.sut.engine.system.SystemUnderTestDeployment;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.spockframework.runtime.extension.IGlobalExtension;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class AbstractSystemUnderTestExtension implements IGlobalExtension {
    public static final String SUT_CERTIFICATE_NAME = "tes-certificate";

    private static SystemUnderTestDeployment systemUnderTestDeployment;

    private final MeterRegistry meterRegistry;
    protected final Path deployDirectory;
    protected final Path certificateDirectory;
    protected final Path logDirectory;
    protected SutCreateTestContainerNetworkStep networkStep;
    protected CertificateStepsResult certificateStepsResult;

    protected AbstractSystemUnderTestExtension() {
        this.meterRegistry = new SimpleMeterRegistry();
        this.deployDirectory = FileSystems.getDefault().getPath("build", "deploy");
        this.certificateDirectory = FileSystems.getDefault().getPath(deployDirectory.toAbsolutePath().toString(), "certificates");
        this.logDirectory = FileSystems.getDefault().getPath(deployDirectory.toAbsolutePath().toString(), "logs");
    }

    protected abstract List<FlowStep> configureDeploySteps(SystemUnderTestDeployment systemUnderTestDeployment);

    public static Optional<SystemUnderTestDeployment> systemUnderTestDeployment() {
        return Optional.ofNullable(systemUnderTestDeployment);
    }

    private static void setSystemUnderTestDeployment(SystemUnderTestDeployment newSystemUnderTestDeployment) {
        systemUnderTestDeployment = newSystemUnderTestDeployment;
    }

    @Override
    public void start() {
        log.info("Starting deploying System Under Test Extension");

        try {
            IGlobalExtension.super.start();
            setSystemUnderTestDeployment(new SystemUnderTestDeployment());

            DeleteDirectoryStep deleteDirectoryStep = new DeleteDirectoryStep("deleteDeployDirectory");
            deleteDirectoryStep.setDirectory(new AtomicDataSupplier<>(deployDirectory));

            networkStep = new SutCreateTestContainerNetworkStep("createNetwork");

            CertificateStepsFactory certificateStepsFactory = new CertificateStepsFactory(certificateDirectory);
            certificateStepsResult = certificateStepsFactory.createSteps("tesCertificate");

            systemUnderTestDeployment.addCertificate(SutCertificate.builder()
                .key(SUT_CERTIFICATE_NAME)
                .certificate(certificateStepsResult.getCertificateStep().getCreatedCertificate())
                .build()
            );
            log.info("Add certificate to System Under Test");

            List<FlowStep> steps = new ArrayList<>();
            steps.add(deleteDirectoryStep);
            steps.add(networkStep);
            steps.addAll(certificateStepsResult.steps());
            steps.addAll(configureDeploySteps(systemUnderTestDeployment));

            SequenceFlow deployFlow = new SequenceFlow(meterRegistry, "SystemUnderTestExtension.deploy", steps);
            deployFlow.execute();
            deployFlow.printReport();

            log.info("System Under Test has been deployed with these services:");
            log.info("========================================================");
            systemUnderTestDeployment.getServices().forEach(service -> {
                log.info("Service '{}': {}. Docker image: {}", service.key(), service.container().getContainerName(), service.container().getDockerImageName());
                service.container().getExposedPorts().forEach(exposedPort -> {
                    log.info("{}Port {} -> {}", " ".repeat(4), exposedPort, service.container().getMappedPort(exposedPort));
                });
            });
        } catch (Exception e) {
            log.error("Error starting System Under Test Extension", e);
            throw e;
        }
    }

    @Override
    public void stop() {
        log.info("Stop deployed System Under Test Extension");

        try {
            IGlobalExtension.super.stop();

            systemUnderTestDeployment().ifPresent(instance -> {
                instance.undeploy(meterRegistry, "SystemUnderTestExtension.undeploy");
                setSystemUnderTestDeployment(null);
            });
        } catch (Exception e) {
            log.error("Error stopping System Under Test Extension", e);
            throw e;
        }
    }

}
