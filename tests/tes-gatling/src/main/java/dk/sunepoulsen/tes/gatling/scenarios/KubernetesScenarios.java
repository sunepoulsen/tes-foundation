package dk.sunepoulsen.tes.gatling.scenarios;

import io.gatling.javaapi.core.PopulationBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;
import java.util.List;

import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

/** Gatling scenarios for Kubernetes liveness and readiness probes. */
public class KubernetesScenarios {

    /** HTTP configuration shared by the probe scenarios. */
    private final HttpProtocolBuilder httpProtocolBuilder;

    /**
     * Configures probe requests against a base URL.
     * @param url base URL of the service
     */
    public KubernetesScenarios(String url) {
        this.httpProtocolBuilder = http.baseUrl(url)
            .userAgentHeader("kube-probe/1.30")
            .acceptHeader("*/*");

    }

    /**
     * Creates liveness and readiness populations, each injecting 0.2 users per second.
     * @param duration duration of each population
     * @return the two configured populations
     */
    public List<PopulationBuilder> populate(Duration duration) {
        return List.of(
            livenessScenario().injectOpen(
                constantUsersPerSec(0.2).during(duration)
            ).protocols(httpProtocolBuilder),
            readinessScenario().injectOpen(
                constantUsersPerSec(0.2).during(duration)
            ).protocols(httpProtocolBuilder)
        );
    }

    private ScenarioBuilder livenessScenario() {
        return scenario("Kubernetes-liveness")
            .exec(
                http("liveness-probe")
                    .get("/actuator/health/liveness")
                    .check(status().is(200))
            );
    }

    private ScenarioBuilder readinessScenario() {
        return scenario("Kubernetes-readiness")
            .exec(
                http("readiness-probe")
                    .get("/actuator/health/readiness")
                    .check(status().is(200))
            );
    }

}
