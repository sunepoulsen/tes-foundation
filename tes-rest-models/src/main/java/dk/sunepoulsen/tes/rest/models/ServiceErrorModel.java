package dk.sunepoulsen.tes.rest.models;

import lombok.Data;

@Data
public class ServiceErrorModel implements BaseModel {
    private String code;
    private String param;
    private String message;
}
