package dk.sunepoulsen.tes.rest.models.monitoring;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * Model to represents bodies from the endpoint <code>/actuator/metrics</code>.
 */
@Data
public class ServiceMetrics {
    /**
     * Lists the names of all metrics that is supported by the Spring Boot backend.
     */
    @NotNull
    private List<String> names;
}
