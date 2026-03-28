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

    private static final long DEFAULT_CONTAINER_MEMORY_LIMIT = 1024L * 1024L * 1024L; // 1 Gb
    private static final long DEFAULT_CONTAINER_MEMORY_RESERVATION = 256L * 1024L * 1024L; // 256 Mb
    private static final long DEFAULT_CONTAINER_SWAP_LIMIT = 2048L * 1024L * 1024L; // 2 Gb swap

    /**
     * Specify the CPU CFS scheduler period, which is used alongside
     * {@code --cpu-quota}. Defaults to 100000 microseconds (100 milliseconds). Most users don't change this from
     * the default. For most use-cases, {@code --cpus} is a more convenient alternative.
     */
    private static final long DEFAULT_CONTAINER_CPU_PERIOD = 100_000L;  // 100ms

    /**
     * Impose a CPU CFS quota on the container. The number of microseconds per {@code --cpu-period} that the
     * container is limited to before being throttled. As such acting as the effective ceiling. For most
     * use-cases, {@code --cpus} is a more convenient alternative.
     * <p>
     * A value of 100000 is 100% of a CPU.
     */
    private static final long DEFAULT_CONTAINER_CPU_QUOTA = 150 * 1_000L;

    protected final String serviceKey;
    private AtomicDataSupplier<String> dockerImageName;
    private AtomicDataSupplier<String> dockerImageTag;
    private AtomicDataSupplier<Long> memoryLimit;
    private AtomicDataSupplier<Long> memoryReservation;
    private AtomicDataSupplier<Long> memorySwapLimit;
    private AtomicDataSupplier<Long> cpuPeriod;
    private AtomicDataSupplier<Long> cpuQuota;
    private Map<String, AtomicDataSupplier<String>> environmentVariables;
    private List<AtomicDataSupplier<String>> aliases;
    private AtomicDataSupplier<Network> network;
    private AtomicDataSupplier<Path> logPath;
    protected final SystemUnderTestDeployment systemUnderTestDeployment;

    protected AbstractSutContainerStep(String key, String serviceKey, SystemUnderTestDeployment systemUnderTestDeployment) {
        super(key);
        this.serviceKey = serviceKey;
        this.memoryLimit = new AtomicDataSupplier<>(DEFAULT_CONTAINER_MEMORY_LIMIT);
        this.memoryReservation = new AtomicDataSupplier<>(DEFAULT_CONTAINER_MEMORY_RESERVATION);
        this.memorySwapLimit = new AtomicDataSupplier<>(DEFAULT_CONTAINER_SWAP_LIMIT);
        this.cpuPeriod = new AtomicDataSupplier<>(DEFAULT_CONTAINER_CPU_PERIOD);
        this.cpuQuota = new AtomicDataSupplier<>(DEFAULT_CONTAINER_CPU_QUOTA);
        this.environmentVariables = new HashMap<>();
        this.aliases = new ArrayList<>();
        this.systemUnderTestDeployment = systemUnderTestDeployment;
    }

    protected GenericContainer<?> createContainer() {
        final String imageName = dockerImageName.get("Docker Image name has not been set");
        final String imageTag = dockerImageTag.get("Docker Image tag has not been set");

        final GenericContainer<?> container = new GenericContainer<>(DockerImageName.parse(imageName).withTag(imageTag));
        container.withCreateContainerCmdModifier(cmd ->
            Objects.requireNonNull(cmd.getHostConfig())
                .withMemory(memoryLimit.get("Container memory limit has not been set"))
                .withMemoryReservation(memoryReservation.get("Container memory reservation has not been set"))
                .withMemorySwap(memorySwapLimit.get("Container memory swap limit has not been set"))
                .withCpuPeriod(cpuPeriod.get("Container CPU period has not been set"))
                .withCpuQuota(cpuQuota.get("Container CPU quota has not been set"))
        );

        environmentVariables.forEach((name, valueSupplier) ->
            container.withEnv(name, valueSupplier.get("Value of environment variable '" + name + "' as not been set"))
        );

        aliases.forEach(aliasSupplier ->
            container.withNetworkAliases(aliasSupplier.get("Alias has not been set"))
        );
        container.withNetwork(network.get("Docker network has not been set"));

        return container;
    }

    protected <T extends GenericContainer<T>> GenericContainer<T> withMountFiles(GenericContainer<T> container, String containerPath, List<AtomicDataSupplier<Path>> paths) {
        paths.forEach(pathSupplier -> {
            Path path = pathSupplier.get("Path has not been set for mounted file");
            container.withCopyFileToContainer(MountableFile.forHostPath(path), containerPath + "/" + path.getFileName());
        });

        return container;
    }
}
