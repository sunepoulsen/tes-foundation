package dk.sunepoulsen.tes.rest.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude( JsonInclude.Include.NON_NULL )
@JsonNaming( PropertyNamingStrategies.SnakeCaseStrategy.class )
@JsonIgnoreProperties( ignoreUnknown = true )
public interface BaseModel {
}
