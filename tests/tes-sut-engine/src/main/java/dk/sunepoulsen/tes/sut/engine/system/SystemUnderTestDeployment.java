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

/** Holds the services and context of a system-under-test deployment. */
@Slf4j
@Data
public class SystemUnderTestDeployment {

    /** Deployment profiles. */
    private List<String> profiles;
    /** Services in deployment order. */
    private List<SutService> services;
    /** Named deployment context values. */
    private Map<String, Object> context;

    /** Creates an empty deployment. */
    public SystemUnderTestDeployment() {
        this.profiles = new ArrayList<>();
        this.services = new ArrayList<>();
        this.context = new HashMap<>();
    }

    /**
     * Adds a context value using an exact key.
     * @param key context key
     * @param value value to store
     * @throws IllegalStateException if the exact key already exists
     */
    public void putContext(final String key, final Object value) {
        if (context.containsKey(key)) {
            throw new IllegalStateException("This SystemUnderTestDeployment already contains the key '%s' in its context".formatted(key));
        }

        context.put(key, value);
    }

    /**
     * Finds a context value by case-insensitive key and requested type.
     * @param <T> requested value type
     * @param key context key
     * @param clazz requested value class
     * @return the matching value, or an empty optional if absent or of another type
     */
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

    /**
     * Adds a service to the deployment.
     * @param service service to add
     */
    public void addService(SutService service) {
        services.add(service);
    }

    /**
     * Finds the first service matching a case-insensitive key and type.
     * @param <T> requested service type
     * @param key service key
     * @param clazz requested service class
     * @return the matching service, or an empty optional
     */
    public <T extends SutService> Optional<T> findService(String key, Class<T> clazz) {
        return services.stream()
            .filter(service -> key.equalsIgnoreCase(service.key()))
            .filter(clazz::isInstance)
            .map(clazz::cast)
            .findFirst();
    }

    /**
     * Executes service undeployment steps in reverse order, followed by Docker cleanup.
     * @param meterRegistry registry for flow metrics
     * @param timerName name used for flow timing
     */
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
