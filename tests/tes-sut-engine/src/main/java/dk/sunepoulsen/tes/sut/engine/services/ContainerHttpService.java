package dk.sunepoulsen.tes.sut.engine.services;

import dk.sunepoulsen.tes.flows.FlowStep;
import lombok.*;
import org.testcontainers.containers.GenericContainer;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Function;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContainerHttpService extends ContainerService implements SutHttpService {
    private final ContainerProtocol protocol;

    public ContainerHttpService(String key, GenericContainer<?> container, ContainerProtocol protocol, Function<SutService, FlowStep> undeployStepFunction) {
        super(key, container, undeployStepFunction);
        this.protocol = protocol;
    }

    @Override
    public ContainerProtocol protocol() {
        return this.protocol;
    }

    @Override
    public URI baseUrl() throws URISyntaxException {
        return baseUrl(8080);
    }

    @Override
    public URI baseUrl(int containerPort) throws URISyntaxException {
        return protocol().baseUrl("localhost", container().getMappedPort(containerPort));
    }
}
