package dk.sunepoulsen.tes.rest.integrations;

import dk.sunepoulsen.tes.rest.models.monitoring.ServiceHealth;
import dk.sunepoulsen.tes.rest.models.monitoring.ServiceInfo;
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

}
