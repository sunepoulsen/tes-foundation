package dk.sunepoulsen.tes.sut.engine.services;

import java.net.URI;
import java.net.URISyntaxException;

/** A container-backed service accessible over HTTP or HTTPS. */
public interface SutHttpService extends SutService {
    /** @return the service protocol */
    ContainerProtocol protocol();
    /**
     * Returns the default base URL.
     * @return the service base URL
     * @throws URISyntaxException if the URL is invalid
     */
    URI baseUrl() throws URISyntaxException;
    /**
     * Returns the base URL for a container port.
     * @param containerPort port exposed by the container
     * @return the service base URL
     * @throws URISyntaxException if the URL is invalid
     */
    URI baseUrl(int containerPort) throws URISyntaxException;
}
