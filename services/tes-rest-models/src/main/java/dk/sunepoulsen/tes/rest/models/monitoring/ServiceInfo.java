package dk.sunepoulsen.tes.rest.models.monitoring;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ServiceInfo {
    @NotNull
    @Valid
    private ServiceInfoApp app;
}
