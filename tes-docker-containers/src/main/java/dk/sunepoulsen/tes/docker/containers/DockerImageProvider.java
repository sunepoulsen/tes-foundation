package dk.sunepoulsen.tes.docker.containers;

import dk.sunepoulsen.tes.docker.exceptions.DockerImageProviderException;
import org.testcontainers.utility.DockerImageName;

/**
 * This interface defines the api to provide a docker image with a name and tag.
 */
public interface DockerImageProvider {
    /**
     * Returns the docker image name to use to create a docker container.
     */
    DockerImageName dockerImageName() throws DockerImageProviderException;
}
