package dk.sunepoulsen.tes.rest.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Schema(name = "List model", description = "Model of a list of results")
@Data
public class EnvelopeModel<T> {
    /**
     * List of results
     */
    @Schema(
        description = "List of elements",
        requiredMode = Schema.RequiredMode.REQUIRED,
        accessMode = Schema.AccessMode.READ_ONLY
    )
    @NotNull
    @Valid
    private List<T> results;
}
