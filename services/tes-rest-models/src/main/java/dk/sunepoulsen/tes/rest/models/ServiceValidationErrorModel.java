package dk.sunepoulsen.tes.rest.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/** Service error containing individual validation errors. */
@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper = true)
@Schema(name = "ServiceValidationErrorModel", description = "Defines service validation errors")
public class ServiceValidationErrorModel extends ServiceErrorModel {
    /** Validation errors associated with the service error. */
    @Schema(
        description = "List of validation errors",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private List<ServiceValidationError> validationErrors;
}
