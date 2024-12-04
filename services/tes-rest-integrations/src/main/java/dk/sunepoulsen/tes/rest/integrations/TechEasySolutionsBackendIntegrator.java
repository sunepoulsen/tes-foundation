package dk.sunepoulsen.tes.rest.integrations;

import dk.sunepoulsen.tes.rest.models.HashMapModel;
import dk.sunepoulsen.tes.rest.models.monitoring.ServiceHealth;
import dk.sunepoulsen.tes.rest.models.monitoring.ServiceInfo;
import dk.sunepoulsen.tes.rest.models.monitoring.ServiceMetrics;
import io.reactivex.rxjava3.core.Single;

public class TechEasySolutionsBackendIntegrator extends AbstractIntegrator {
    public TechEasySolutionsBackendIntegrator(TechEasySolutionsClient httpClient) {
        super(httpClient);
    }

    public Single<ServiceHealth> health() {
        return Single.fromFuture(httpClient.get("/actuator/health", ServiceHealth.class))
                .onErrorResumeNext(this::mapClientExceptions);
    }

    public Single<ServiceInfo> info() {
        return Single.fromFuture(httpClient.get("/actuator/info", ServiceInfo.class))
            .onErrorResumeNext(this::mapClientExceptions);
    }

    public Single<HashMapModel> env() {
        return Single.fromFuture(httpClient.get("/actuator/env", HashMapModel.class))
            .onErrorResumeNext(this::mapClientExceptions);
    }

    public Single<ServiceMetrics> metrics() {
        return Single.fromFuture(httpClient.get("/actuator/metrics", ServiceMetrics.class))
            .onErrorResumeNext(this::mapClientExceptions);
    }

    public Single<HashMapModel> metric(String name) {
        return Single.fromFuture(httpClient.get("/actuator/metrics/" + name, HashMapModel.class))
            .onErrorResumeNext(this::mapClientExceptions);
    }

}
