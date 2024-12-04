package dk.sunepoulsen.tes.docker.containers;

import org.testcontainers.containers.wait.strategy.WaitStrategy;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Interface to implement different protocols that a container can use.
 *
 * Examples of this is http and https.
 */
public interface TESContainerProtocol {
    /**
     * Returns the exposed port of the container for this protocol.
     */
    int exposedPort();

    /**
     * Returns the wait strategy to use to wait for the container to start at startup.
     *
     * @param endpoint The endpoint to call while waiting for the container to start.
     */
    WaitStrategy waitStrategy(String endpoint);

    /**
     * Returns the base url for a container that uses this protocol.
     *
     * @param host The host of the container.
     * @param port The port to access the container from the host.
     *
     * @return Returns an complete URI with the base url.
     */
    URI baseUrl(String host, Integer port) throws URISyntaxException;
}
