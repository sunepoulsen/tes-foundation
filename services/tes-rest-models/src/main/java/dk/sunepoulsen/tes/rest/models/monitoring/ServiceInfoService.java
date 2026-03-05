package dk.sunepoulsen.tes.rest.models.monitoring;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ServiceInfoService {
    @NotNull
    @NotBlank
    private String name;
}
