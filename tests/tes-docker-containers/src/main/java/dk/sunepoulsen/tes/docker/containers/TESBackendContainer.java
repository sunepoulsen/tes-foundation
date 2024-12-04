package dk.sunepoulsen.tes.docker.containers;

import dk.sunepoulsen.tes.docker.exceptions.DockerImageProviderException;
import dk.sunepoulsen.tes.rest.integrations.TechEasySolutionsBackendIntegrator;
import dk.sunepoulsen.tes.rest.integrations.TechEasySolutionsClient;
import dk.sunepoulsen.tes.rest.integrations.config.TechEasySolutionsClientConfig;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import java.net.URI;
import java.net.URISyntaxException;

public class TESBackendContainer extends GenericContainer<TESBackendContainer> {

    private TESContainerProtocol protocol;

    public TESBackendContainer(DockerImageProvider dockerImageProvider, String springProfile) throws DockerImageProviderException {
        this(dockerImageProvider, new TESContainerUnsecureProtocol(), springProfile);
    }

    public TESBackendContainer(DockerImageProvider dockerImageProvider, TESContainerProtocol protocol, String springProfile) throws DockerImageProviderException {
        super(dockerImageProvider.dockerImageName());
        this.protocol = protocol;

        withEnv("SPRING_PROFILES_ACTIVE", springProfile);
        withExposedPorts(this.protocol.exposedPort());

        waitingFor(
            this.protocol.waitStrategy("/actuator/health")
        );
    }

    public TESBackendContainer withConfigMapping(String classPathFilename) {
        withClasspathResourceMapping(classPathFilename, "/app/resources/" + classPathFilename, BindMode.READ_ONLY);
        return this;
    }

    public void copyLogFile(String destinationPath) {
        copyFileFromContainer("/app/logs/service.log", destinationPath);
    }

    public URI baseUrl() throws URISyntaxException {
        return this.protocol.baseUrl(getHost(), getMappedPort(this.protocol.exposedPort()) );
    }

    public TechEasySolutionsClient createClient() throws URISyntaxException {
        return new TechEasySolutionsClient(baseUrl());
    }

    public TechEasySolutionsClient createClient(TechEasySolutionsClientConfig config) throws URISyntaxException {
        return new TechEasySolutionsClient(baseUrl(), config);
    }

    public TechEasySolutionsBackendIntegrator createGenericIntegrator() throws URISyntaxException {
        return new TechEasySolutionsBackendIntegrator(createClient());
    }
}
