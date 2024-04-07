package dk.sunepoulsen.tes.rest.models;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class PaginationMetaData implements BaseModel {
    @PositiveOrZero
    private int page;

    @PositiveOrZero
    private int totalPages;

    @PositiveOrZero
    private long totalItems;

    @PositiveOrZero
    private int size;
}
