package dk.sunepoulsen.tes.rest.models.monitoring;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(name = "ServiceInfoApp", description = "Defines the information about an application")
public class ServiceInfoApp {
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
