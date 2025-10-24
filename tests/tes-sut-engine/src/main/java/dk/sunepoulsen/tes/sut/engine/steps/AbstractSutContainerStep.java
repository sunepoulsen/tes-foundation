package dk.sunepoulsen.tes.sut.engine.steps;

import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier;
import dk.sunepoulsen.tes.deployment.core.steps.AbstractDeployStep;
import dk.sunepoulsen.tes.sut.engine.system.SystemUnderTestDeployment;
import lombok.Getter;
import lombok.Setter;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import java.nio.file.Path;
import java.util.*;

@Getter
@Setter
public abstract class AbstractSutContainerStep extends AbstractDeployStep {

    protected final String serviceKey;
    private AtomicDataSupplier<String> dockerImageName;
    private AtomicDataSupplier<String> dockerImageTag;
    private Map<String, AtomicDataSupplier<String>> environmentVariables;
    private List<AtomicDataSupplier<String>> aliases;
    private AtomicDataSupplier<Network> network;
    private AtomicDataSupplier<Path> logPath;
    protected final SystemUnderTestDeployment systemUnderTestDeployment;

    protected AbstractSutContainerStep(String key, String serviceKey, SystemUnderTestDeployment systemUnderTestDeployment) {
        super(key);
        this.serviceKey = serviceKey;
        this.environmentVariables = new HashMap<>();
        this.aliases = new ArrayList<>();
        this.systemUnderTestDeployment = systemUnderTestDeployment;
    }

    protected GenericContainer<?> createContainer() {
        final String imageName = dockerImageName.get("Docker Image name has not been set");
        final String imageTag = dockerImageTag.get("Docker Image tag has not been set");

        final GenericContainer<?> container = new GenericContainer<>(DockerImageName.parse(imageName).withTag(imageTag));

        environmentVariables.forEach((name, valueSupplier) ->
            container.withEnv(name, valueSupplier.get("Value of environment variable '" + name + "' as not been set"))
        );

        aliases.forEach(aliasSupplier ->
            container.withNetworkAliases(aliasSupplier.get("Alias has not been set"))
        );
        container.withNetwork(network.get("Docker network has not been set"));

        return container;
    }

    protected GenericContainer<?> withMountFiles(GenericContainer<?> container, String containerPath, List<AtomicDataSupplier<Path>> paths) {
        paths.forEach(pathSupplier -> {
            Path path = pathSupplier.get("Path has not been set for mounted file");
            container.withCopyFileToContainer(MountableFile.forHostPath(path), containerPath + "/" + path.getFileName());
        });

        return container;
    }
}
