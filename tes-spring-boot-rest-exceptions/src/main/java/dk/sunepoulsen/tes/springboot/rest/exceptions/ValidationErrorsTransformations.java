package dk.sunepoulsen.tes.springboot.rest.exceptions;

import dk.sunepoulsen.tes.rest.models.ServiceValidationError;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ElementKind;
import jakarta.validation.Path;
import org.springframework.validation.FieldError;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ValidationErrorsTransformations {

    private ValidationErrorsTransformations() {
    }

    static List<ServiceValidationError> toServiceValidationErrors(ConstraintViolationException ex) {
        return ex.getConstraintViolations().stream()
            .map(constraintViolation -> {
                ServiceValidationError error = new ServiceValidationError();
                error.setParam(constructViolationPath(constraintViolation.getPropertyPath()));
                error.setMessage(constraintViolation.getMessage());

                return error;
            })
            .sorted(validationErrorComparator())
            .toList();
    }

    static List<ServiceValidationError> toServiceValidationErrors(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
            .map(fieldError -> {
                ServiceValidationError error = new ServiceValidationError();
                error.setParam(fieldError.getField());
                error.setMessage(fieldError.getDefaultMessage());

                return error;
            } )
            .sorted(validationErrorComparator())
            .toList();
    }

    private static Comparator<ServiceValidationError> validationErrorComparator() {
        return Comparator.comparing(ServiceValidationError::getParam)
            .thenComparing(ServiceValidationError::getMessage);
    }

    private static String constructViolationPath(Path path) {
        return StreamSupport.stream(path.spliterator(), false)
            .filter(node -> node.getKind() == ElementKind.PROPERTY)
            .map(Path.Node::getName)
            .collect(Collectors.joining("."));
    }

}
