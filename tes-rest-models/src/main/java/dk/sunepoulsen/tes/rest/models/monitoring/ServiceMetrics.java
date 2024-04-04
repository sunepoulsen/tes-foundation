package dk.sunepoulsen.tes.rest.models.monitoring;

import dk.sunepoulsen.tes.rest.models.BaseModel;
import lombok.Data;

import java.util.List;

/**
 * Model to represents bodies from the endpoint <code>/actuator/metrics</code>.
 */
@Data
public class ServiceMetrics implements BaseModel {
    /**
     * Lists the names of all metrics that is supported by the Spring Boot backend.
     */
    private List<String> names;
}
