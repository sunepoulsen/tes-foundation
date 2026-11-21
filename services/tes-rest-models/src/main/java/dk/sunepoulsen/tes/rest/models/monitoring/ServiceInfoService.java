package dk.sunepoulsen.tes.rest.models.monitoring;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/** Identifying information about a service. */
@Data
@Schema(name = "ServiceInfoService", description = "Defines the information about a service")
public class ServiceInfoService {
    /** Service name. */
    @NotNull
    @NotBlank
    private String name;
}
