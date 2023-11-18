package dk.sunepoulsen.tes.docker.containers;

import dk.sunepoulsen.tes.rest.integrations.TechEasySolutionsBackendIntegrator;
import dk.sunepoulsen.tes.rest.integrations.TechEasySolutionsClient;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import java.net.URI;
import java.net.URISyntaxException;

public class TESBackendContainer extends GenericContainer<TESBackendContainer> {
    public TESBackendContainer(String imageName, String tagName, String springProfile) {
        super(DockerImageName.parse(imageName + ":" + tagName));

        withEnv("SPRING_PROFILES_ACTIVE", springProfile);
        withExposedPorts(8080);

        waitingFor(
            Wait.forHttp("/actuator/health")
                .forStatusCode(200)
        );
    }

    public TESBackendContainer withConfigMapping(String classPathFilename) {
        withClasspathResourceMapping(classPathFilename, "/app/resources/" + classPathFilename, BindMode.READ_ONLY);
        return this;
    }

    public void copyLogFile(String destinationPath) {
        copyFileFromContainer("/app/logs/service.log", destinationPath);
    }

    public TechEasySolutionsClient createClient() throws URISyntaxException {
        String baseUrl = String.format("http://%s:%s", getHost(), getMappedPort(8080) );
        return new TechEasySolutionsClient(new URI(baseUrl));
    }

    public TechEasySolutionsBackendIntegrator createGenericIntegrator() throws URISyntaxException {
        return new TechEasySolutionsBackendIntegrator(createClient());
    }
}
