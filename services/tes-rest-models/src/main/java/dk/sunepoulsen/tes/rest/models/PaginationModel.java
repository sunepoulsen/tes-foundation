package dk.sunepoulsen.tes.rest.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * Object with a list of results
 *
 * @param <T> Type of each item in the list of results.
 */
@Schema(name = "Pagination model", description = "Model of a paginated result")
@Data
public class PaginationModel<T> implements BaseModel {
    @Schema(
        description = "Metadata of this paginated result",
        requiredMode = Schema.RequiredMode.REQUIRED,
        accessMode = Schema.AccessMode.READ_ONLY
    )
    @NotNull
    @Valid
    private PaginationMetaData metadata;

    /**
     * List of results
     */
    @Schema(
        description = "List of elements in the paginated result",
        requiredMode = Schema.RequiredMode.REQUIRED,
        accessMode = Schema.AccessMode.READ_ONLY
    )
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@modelClass")
    @NotNull
    @Valid
    private List<T> results;

    public PaginationModel() {
        this.metadata = new PaginationMetaData();
    }
}
