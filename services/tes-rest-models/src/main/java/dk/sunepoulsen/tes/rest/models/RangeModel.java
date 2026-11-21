package dk.sunepoulsen.tes.rest.models;

import dk.sunepoulsen.tes.rest.models.validation.constraints.Interval;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

/**
 * Model representing a numeric range.
 *
 * @param <T> type of the range bounds
 */
@Data
@Interval(minProperty = "min", maxProperty = "max")
@Schema(name = "RangeModel", description = "Defines a range of min and max")
public class RangeModel<T extends Number> {
    /** Minimum value of the range. */
    @Schema(
        description = "Minimum value of this range",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        accessMode = Schema.AccessMode.READ_WRITE
    )
    @PositiveOrZero
    private T min;

    /** Maximum value of the range. */
    @Schema(
        description = "Maximum value of this range",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        accessMode = Schema.AccessMode.READ_WRITE
    )
    @PositiveOrZero
    private T max;
}
