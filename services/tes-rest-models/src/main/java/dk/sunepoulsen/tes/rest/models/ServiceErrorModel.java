package dk.sunepoulsen.tes.rest.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(name = "ServiceErrorModel", description = "Defines a service error")
public class ServiceErrorModel implements Serializable {
    @Serial
    private static final long serialVersionUID = -7949368391090760504L;

    @Schema(
        description = "Error code of this service error",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private String code;

    @Schema(
        description = "Parameter with the location of the service error",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private String param;

    @Schema(
        description = "Description of the service error",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private String message;
}
