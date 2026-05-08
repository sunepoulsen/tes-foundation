package dk.sunepoulsen.tes.sut.engine.system;

import dk.sunepoulsen.tes.deployment.core.steps.DockerCleanupStep;
import dk.sunepoulsen.tes.flows.FlowStep;
import dk.sunepoulsen.tes.flows.SequenceFlow;
import dk.sunepoulsen.tes.sut.engine.services.SutCertificate;
import dk.sunepoulsen.tes.sut.engine.services.SutService;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@Data
public class SystemUnderTestDeployment {

    private List<String> profiles;
    private List<SutService> services;
    private Map<String, Object> context;

    public SystemUnderTestDeployment() {
        this.profiles = new ArrayList<>();
        this.services = new ArrayList<>();
        this.context = new HashMap<>();
    }

    public void putContext(final String key, final Object value) {
        if (context.containsKey(key)) {
            throw new IllegalStateException("This SystemUnderTestDeployment already contains the key '%s' in its context".formatted(key));
        }

        context.put(key, value);
    }

    public <T> Optional<T> getContext(String key, Class<T> clazz) {
        Optional<String> entryKey = context.keySet().stream()
            .filter(s -> s.equalsIgnoreCase(key))
            .findFirst();

        if (entryKey.isEmpty()) {
            return Optional.empty();
        }

        Object data = context.get(entryKey.get());
        if (clazz.isInstance(data)) {
            return Optional.of(clazz.cast(data));
        }

        return Optional.empty();
    }

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

    public void undeploy(final MeterRegistry meterRegistry, final String timerName) {
        log.info("Configure flow to undeploy System Under Test");
        List<FlowStep> steps = new ArrayList<>(services.reversed().stream()
            .map(SutService::undeployStep)
            .toList());
        steps.add(new DockerCleanupStep("Docker.Cleanup"));

        SequenceFlow flow = new SequenceFlow(meterRegistry, timerName, steps);
        flow.execute();
        flow.printReport();
    }

}
