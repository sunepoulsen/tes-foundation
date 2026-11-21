package dk.sunepoulsen.tes.rest.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/** Model describing an individual service validation error. */
@Data
@Schema(name = "ServiceValidationError", description = "Defines a service validation error")
public class ServiceValidationError implements Serializable {
    /** Serialization version. */
    @Serial
    private static final long serialVersionUID = -1194485686031448130L;

    /** Validation error code. */
    @Schema(
        description = "Error code of this service validation error",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private String code;

    /** Parameter identifying the location of the validation error. */
    @Schema(
        description = "Parameter with the location of the service validation error",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private String param;

    /** Description of the validation error. */
    @Schema(
        description = "Description of the service validation error",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private String message;
}
