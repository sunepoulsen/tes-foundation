package dk.sunepoulsen.tes.sut.engine.services;

import dk.sunepoulsen.tes.flows.FlowStep;
import org.testcontainers.containers.GenericContainer;

public interface SutService {
    String key();
    GenericContainer<?> container();
    FlowStep undeployStep();
}
