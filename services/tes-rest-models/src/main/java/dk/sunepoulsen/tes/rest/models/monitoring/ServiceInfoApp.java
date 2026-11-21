package dk.sunepoulsen.tes.rest.models.monitoring;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/** Application identity and associated service information. */
@Data
@Schema(name = "ServiceInfoApp", description = "Defines the information about an application")
public class ServiceInfoApp {
    /** Application name. */
    @NotNull
    @NotBlank
    private String name;

    /** Application version. */
    @NotNull
    @NotBlank
    private String version;

    /** Information about the service. */
    @NotNull
    @Valid
    private ServiceInfoService service;
}
