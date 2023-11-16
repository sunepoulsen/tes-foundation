package dk.sunepoulsen.tes.rest.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
public class ServiceValidationErrorModel extends ServiceErrorModel {
    private List<ServiceValidationError> validationErrors;
}
