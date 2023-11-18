package dk.sunepoulsen.tes.rest.models.monitoring;

import dk.sunepoulsen.tes.rest.models.BaseModel;
import lombok.Data;

@Data
public class ServiceHealth implements BaseModel {
    private ServiceHealthStatusCode status;
}
