package dk.sunepoulsen.tes.rest.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * Object with a list of results
 *
 * @param <T> Type of each item in the list of results.
 */
@Data
public class PaginationModel<T> implements BaseModel {
    @NotNull
    @Valid
    private PaginationMetaData metadata;

    /**
     * List of results
     */
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@modelClass")
    @NotNull
    @Valid
    private List<T> results;

    public PaginationModel() {
        this.metadata = new PaginationMetaData();
    }
}
