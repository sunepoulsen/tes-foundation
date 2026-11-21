package dk.sunepoulsen.tes.rest.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

/** Metadata describing a page of results. */
@Data
@Schema(name = "PaginationMetaData", description = "Metadata for a paginated result")
public class PaginationMetaData {
    /** Number of the current page. */
    @Schema(
        description = "Number of the current page",
        requiredMode = Schema.RequiredMode.REQUIRED,
        accessMode = Schema.AccessMode.READ_ONLY
    )
    @PositiveOrZero
    private int page;

    /** Total number of pages. */
    @Schema(
        description = "Number of total pages",
        requiredMode = Schema.RequiredMode.REQUIRED,
        accessMode = Schema.AccessMode.READ_ONLY
    )
    @PositiveOrZero
    private int totalPages;

    /** Total number of items across pages. */
    @Schema(
        description = "Total amount of elements",
        requiredMode = Schema.RequiredMode.REQUIRED,
        accessMode = Schema.AccessMode.READ_ONLY
    )
    @PositiveOrZero
    private long totalItems;

    /** Number of items on the current page. */
    @Schema(
        description = "Number of elements on this page",
        requiredMode = Schema.RequiredMode.REQUIRED,
        accessMode = Schema.AccessMode.READ_ONLY
    )
    @PositiveOrZero
    private int size;
}
