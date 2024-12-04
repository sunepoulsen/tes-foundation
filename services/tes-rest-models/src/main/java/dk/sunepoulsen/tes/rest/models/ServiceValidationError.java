package dk.sunepoulsen.tes.rest.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "Service validation error", description = "Defines a service validation error")
@Data
public class ServiceValidationError implements BaseModel {
    @Schema(
        description = "Error code of this service validation error",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private String code;

    @Schema(
        description = "Parameter with the location of the service validation error",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private String param;

    @Schema(
        description = "Description of the service validation error",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private String message;
}
