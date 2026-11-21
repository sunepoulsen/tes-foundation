package dk.sunepoulsen.tes.rest.models.monitoring;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/** Information reported about a service's application. */
@Data
@Schema(name = "ServiceInfo", description = "Defines the information about a service")
public class ServiceInfo {
    /** Application information. */
    @NotNull
    @Valid
    private ServiceInfoApp app;
}
