package dk.sunepoulsen.tes.rest.models.monitoring;

import dk.sunepoulsen.tes.rest.models.BaseModel;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ServiceHealth implements BaseModel {
    @NotNull
    private ServiceHealthStatusCode status;
}
