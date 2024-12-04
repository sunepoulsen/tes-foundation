package dk.sunepoulsen.tes.rest.models.monitoring;

import dk.sunepoulsen.tes.rest.models.BaseModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ServiceInfoApp implements BaseModel {
    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String version;

    @NotNull
    @Valid
    private ServiceInfoService service;
}
