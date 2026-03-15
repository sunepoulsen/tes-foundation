package dk.sunepoulsen.tes.rest.models.monitoring;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ServiceHealth {
    @NotNull
    private ServiceHealthStatusCode status;
}
