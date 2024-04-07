package dk.sunepoulsen.tes.rest.models;

import dk.sunepoulsen.tes.rest.models.validation.constraints.Interval;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Interval(minProperty = "min", maxProperty = "max")
public class RangeModel<T extends Number> implements BaseModel {
    @PositiveOrZero
    private T min;

    @PositiveOrZero
    private T max;
}
