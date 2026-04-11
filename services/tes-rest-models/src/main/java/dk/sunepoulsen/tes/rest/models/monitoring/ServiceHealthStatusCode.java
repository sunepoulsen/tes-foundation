package dk.sunepoulsen.tes.rest.models.monitoring;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ServiceHealthStatusCode", description = "Defines the status of a service")
public enum ServiceHealthStatusCode {
    UP,
    DOWN
}
