package dk.sunepoulsen.tes.rest.models.monitoring;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(name = "ServiceHealth", description = "Defines the health of a service")
public class ServiceHealth {
    @NotNull
    private ServiceHealthStatusCode status;
}
