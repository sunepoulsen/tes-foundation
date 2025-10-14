package dk.sunepoulsen.tes.sut.engine.steps;

import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier;
import dk.sunepoulsen.tes.flows.FlowStepResult;
import dk.sunepoulsen.tes.sut.engine.services.ContainerHttpService;
import dk.sunepoulsen.tes.sut.engine.services.ContainerProtocol;
import dk.sunepoulsen.tes.sut.engine.system.SystemUnderTestDeployment;
import lombok.Getter;
import lombok.Setter;
import org.testcontainers.containers.GenericContainer;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SutStartTesServiceStep extends AbstractSutContainerStep {

    private final ContainerProtocol protocol;
    private AtomicDataSupplier<List<String>> profiles;
    private final List<AtomicDataSupplier<Path>> configurationFiles;
    private final List<AtomicDataSupplier<Path>> certificateFiles;

    public SutStartTesServiceStep(String key, String serviceKey, ContainerProtocol protocol, SystemUnderTestDeployment systemUnderTestDeployment) {
        super(key, serviceKey, systemUnderTestDeployment);
        this.protocol = protocol;
        this.configurationFiles = new ArrayList<>();
        this.certificateFiles = new ArrayList<>();
    }

    @Override
    public FlowStepResult execute() {
        final GenericContainer<?> container = createContainer();

        container.withEnv("SPRING_PROFILES_ACTIVE", String.join(", ", profiles.get("Profiles has not been set")));
        mountFiles(container, "/app/resources", configurationFiles);
        mountFiles(container, "/app/certificates", certificateFiles);
        container.waitingFor(protocol.waitStrategy("/actuator/health"));

        systemUnderTestDeployment.addService(new ContainerHttpService(serviceKey, container, protocol,
            sutService -> new SutShutdownTesServiceStep(getKey() + ".shutdown", getLogPath(), sutService)
        ));
        container.start();

        return FlowStepResult.OK;
    }
}
