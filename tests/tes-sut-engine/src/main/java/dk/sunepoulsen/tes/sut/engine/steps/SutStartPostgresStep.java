package dk.sunepoulsen.tes.sut.engine.steps;

import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier;
import dk.sunepoulsen.tes.flows.FlowStepResult;
import dk.sunepoulsen.tes.sut.engine.services.ContainerService;
import dk.sunepoulsen.tes.sut.engine.system.SystemUnderTestDeployment;
import lombok.Getter;
import lombok.Setter;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;

import java.nio.file.Path;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SutStartPostgresStep extends AbstractSutContainerStep {

    private AtomicDataSupplier<String> masterUsername;
    private AtomicDataSupplier<String> masterPassword;
    private List<AtomicDataSupplier<Path>> startupScripts;

    public SutStartPostgresStep(String key, String serviceKey, SystemUnderTestDeployment systemUnderTestDeployment) {
        super(key, serviceKey, systemUnderTestDeployment);
        this.startupScripts = new ArrayList<>();
    }

    @Override
    public FlowStepResult execute() {
        GenericContainer<?> container = createContainer();

        container.withEnv("POSTGRES_USER", masterUsername.get("Master username has not been set"));
        container.withEnv("POSTGRES_PASSWORD", masterPassword.get("Master password has not been set"));
        container = mountFiles(container, "/docker-entrypoint-initdb.d", startupScripts);
        container.waitingFor((new LogMessageWaitStrategy())
            .withRegEx(".*database system is ready to accept connections.*\\s")
            .withTimes(2)
            .withStartupTimeout(Duration.of(60L, ChronoUnit.SECONDS))
        );

        systemUnderTestDeployment.addService(new ContainerService(serviceKey, container, sutService ->
            new SutShutdownPostgresStep(getKey() + ".shutdown", getLogPath(), sutService)
        ));
        container.start();
        return FlowStepResult.OK;
    }
}
