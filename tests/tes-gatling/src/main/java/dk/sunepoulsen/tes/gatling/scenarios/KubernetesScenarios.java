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

public class KubernetesScenarios {

    private final HttpProtocolBuilder httpProtocolBuilder;

    public KubernetesScenarios(String url) {
        this.httpProtocolBuilder = http.baseUrl(url)
            .userAgentHeader("kube-probe/1.30")
            .acceptHeader("*/*");

    }

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
