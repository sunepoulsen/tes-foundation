package dk.sunepoulsen.tes.rest.models.monitoring;

import dk.sunepoulsen.tes.rest.models.BaseModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ServiceInfoService implements BaseModel {
    @NotNull
    @NotBlank
    private String name;
}
