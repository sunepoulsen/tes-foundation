package dk.sunepoulsen.tes.rest.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Schema(name = "Service validation error", description = "Defines a service validation error")
@Data
public class ServiceValidationError implements Serializable {
    @Serial
    private static final long serialVersionUID = -1194485686031448130L;

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
