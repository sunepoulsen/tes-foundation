package dk.sunepoulsen.tes.rest.models;

import dk.sunepoulsen.tes.rest.models.validation.constraints.Interval;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Schema(name = "Range", description = "Defines a range of min and max")
@Data
@Interval(minProperty = "min", maxProperty = "max")
public class RangeModel<T extends Number> implements BaseModel {
    @Schema(
        description = "Minimum value of this range",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        accessMode = Schema.AccessMode.READ_WRITE
    )
    @PositiveOrZero
    private T min;

    @Schema(
        description = "Maximum value of this range",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        accessMode = Schema.AccessMode.READ_WRITE
    )
    @PositiveOrZero
    private T max;
}
