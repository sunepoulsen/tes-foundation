package dk.sunepoulsen.tes.rest.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Object with a list of results
 *
 * @param <T> Type of each item in the list of results.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(name = "PaginationModel", description = "Model of a paginated result")
public class PaginationModel<T> extends EnvelopeModel<T> {

    @Schema(
        description = "Metadata of this paginated result",
        requiredMode = Schema.RequiredMode.REQUIRED,
        accessMode = Schema.AccessMode.READ_ONLY
    )
    @NotNull
    @Valid
    private PaginationMetaData metadata;

    public PaginationModel() {
        super();
        this.metadata = new PaginationMetaData();
    }

}
