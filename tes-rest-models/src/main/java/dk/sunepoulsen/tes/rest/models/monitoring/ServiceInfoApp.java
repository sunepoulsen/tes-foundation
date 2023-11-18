package dk.sunepoulsen.tes.rest.models.monitoring;

import dk.sunepoulsen.tes.rest.models.BaseModel;
import lombok.Data;

@Data
public class ServiceInfoApp implements BaseModel {
    private String name;
    private String version;
    private ServiceInfoService service;
}
