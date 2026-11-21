package dk.sunepoulsen.tes.rest.integrations;

import dk.sunepoulsen.tes.rest.models.HashMapModel;
import dk.sunepoulsen.tes.rest.models.monitoring.ServiceHealth;
import dk.sunepoulsen.tes.rest.models.monitoring.ServiceInfo;
import dk.sunepoulsen.tes.rest.models.monitoring.ServiceMetrics;
import io.reactivex.rxjava3.core.Single;

/**
 * Client for backend actuator and API documentation endpoints.
 */
public class TechEasySolutionsBackendIntegrator extends AbstractIntegrator {
    /**
     * Creates an integrator using the supplied client.
     *
     * @param httpClient client used to make requests
     */
    public TechEasySolutionsBackendIntegrator(TechEasySolutionsClient httpClient) {
        super(httpClient);
    }

    /**
     * Requests the service health.
     *
     * @return a single emitting the health response
     */
    public Single<ServiceHealth> health() {
        return Single.fromFuture(httpClient.get("/actuator/health", ServiceHealth.class))
                .onErrorResumeNext(this::mapClientExceptions);
    }

    /**
     * Requests the service information.
     *
     * @return a single emitting the information response
     */
    public Single<ServiceInfo> info() {
        return Single.fromFuture(httpClient.get("/actuator/info", ServiceInfo.class))
            .onErrorResumeNext(this::mapClientExceptions);
    }

    /**
     * Requests the service environment.
     *
     * @return a single emitting the environment response
     */
    public Single<HashMapModel> env() {
        return Single.fromFuture(httpClient.get("/actuator/env", HashMapModel.class))
            .onErrorResumeNext(this::mapClientExceptions);
    }

    /**
     * Requests the available metrics.
     *
     * @return a single emitting the metrics response
     */
    public Single<ServiceMetrics> metrics() {
        return Single.fromFuture(httpClient.get("/actuator/metrics", ServiceMetrics.class))
            .onErrorResumeNext(this::mapClientExceptions);
    }

    /**
     * Requests a named metric.
     *
     * @param name metric name
     * @return a single emitting the metric response
     */
    public Single<HashMapModel> metric(String name) {
        return Single.fromFuture(httpClient.get("/actuator/metrics/" + name, HashMapModel.class))
            .onErrorResumeNext(this::mapClientExceptions);
    }

    /**
     * Requests the OpenAPI documentation.
     *
     * @return a single emitting the documentation response
     */
    public Single<HashMapModel> apiDocumentation() {
        return Single.fromFuture(httpClient.get("/v3/api-docs", HashMapModel.class))
            .onErrorResumeNext(this::mapClientExceptions);
    }

}
