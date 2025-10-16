package dk.sunepoulsen.tes.sut.engine.services;

import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.containers.wait.strategy.WaitStrategy;

import java.net.URI;
import java.net.URISyntaxException;

public class ContainerSecureProtocol implements ContainerProtocol {
    @Override
    public int exposedPort() {
        return 8080;
    }

    @Override
    public WaitStrategy waitStrategy(String endpoint) {
        return Wait.forHttps(endpoint)
            .allowInsecure()
            .forStatusCode(200);
    }

    @Override
    public URI baseUrl(String host, Integer port) throws URISyntaxException {
        return new URI(String.format("https://%s:%s", host, port));
    }
}
