package dk.sunepoulsen.tes.rest.models.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

public class DefaultValidator {

    private final ValidatorFactory factory;
    private final Validator validator;

    public DefaultValidator() {
        this(Validation.buildDefaultValidatorFactory());
    }

    public DefaultValidator(ValidatorFactory factory) {
        this.factory = factory;
        this.validator = factory.getValidator();
    }

    public <T> void validate(T value, Class<?>... groups) {
        Set<ConstraintViolation<T>> violations = this.validator.validate(value, groups);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

}
