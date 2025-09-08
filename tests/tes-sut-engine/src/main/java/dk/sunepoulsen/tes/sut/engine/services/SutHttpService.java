package dk.sunepoulsen.tes.sut.engine.services;

import java.net.URI;
import java.net.URISyntaxException;

public interface SutHttpService extends SutService {
    ContainerProtocol protocol();
    URI baseUrl() throws URISyntaxException;
    URI baseUrl(int containerPort) throws URISyntaxException;
}
