package dk.sunepoulsen.tes.rest.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Schema(name = "Pagination Metadata", description = "Metadata for a paginated result")
@Data
public class PaginationMetaData implements BaseModel {
    @Schema(
        description = "Number of the current page",
        requiredMode = Schema.RequiredMode.REQUIRED,
        accessMode = Schema.AccessMode.READ_ONLY
    )
    @PositiveOrZero
    private int page;

    @Schema(
        description = "Number of total pages",
        requiredMode = Schema.RequiredMode.REQUIRED,
        accessMode = Schema.AccessMode.READ_ONLY
    )
    @PositiveOrZero
    private int totalPages;

    @Schema(
        description = "Total amount of elements",
        requiredMode = Schema.RequiredMode.REQUIRED,
        accessMode = Schema.AccessMode.READ_ONLY
    )
    @PositiveOrZero
    private long totalItems;

    @Schema(
        description = "Number of elements on this page",
        requiredMode = Schema.RequiredMode.REQUIRED,
        accessMode = Schema.AccessMode.READ_ONLY
    )
    @PositiveOrZero
    private int size;
}
