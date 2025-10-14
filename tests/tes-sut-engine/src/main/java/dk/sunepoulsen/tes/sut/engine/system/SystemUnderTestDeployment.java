package dk.sunepoulsen.tes.sut.engine.system;

import dk.sunepoulsen.tes.flows.FlowStep;
import dk.sunepoulsen.tes.flows.SequenceFlow;
import dk.sunepoulsen.tes.sut.engine.services.SutCertificate;
import dk.sunepoulsen.tes.sut.engine.services.SutService;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class SystemUnderTestDeployment {

    private List<String> profiles;
    private List<SutService> services;
    private List<SutCertificate> certificates;

    public void addService(SutService service) {
        services.add(service);
    }

    public <T extends SutService> Optional<T> findService(String key, Class<T> clazz) {
        return services.stream()
            .filter(service -> key.equalsIgnoreCase(service.key()))
            .filter(clazz::isInstance)
            .map(clazz::cast)
            .findFirst();
    }

    public void addCertificate(SutCertificate certificate) {
        certificates.add(certificate);
    }

    public Optional<SutCertificate> findCertificate(String key) {
        return certificates.stream()
            .filter(certificate -> key.equalsIgnoreCase(certificate.getKey()))
            .findFirst();
    }

    public void undeploy(final MeterRegistry meterRegistry, final String timerName) {
        List<FlowStep> steps = services.reversed().stream()
            .map(SutService::undeployStep)
            .toList();

        SequenceFlow flow = new SequenceFlow(meterRegistry, timerName, steps);
        flow.execute();
    }

}
