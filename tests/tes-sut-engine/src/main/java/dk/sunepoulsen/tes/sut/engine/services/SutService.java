package dk.sunepoulsen.tes.sut.engine.services;

import dk.sunepoulsen.tes.flows.FlowStep;
import org.testcontainers.containers.GenericContainer;

/** A service in the system under test backed by a container. */
public interface SutService {
    /** @return the service key */
    String key();
    /** @return the service container */
    GenericContainer<?> container();
    /** @return the flow step used to undeploy the service */
    FlowStep undeployStep();
}
