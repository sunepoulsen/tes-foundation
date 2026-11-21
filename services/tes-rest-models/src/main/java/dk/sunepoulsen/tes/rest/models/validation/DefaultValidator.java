package dk.sunepoulsen.tes.rest.models.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

/** Validates objects using Jakarta Bean Validation. */
public class DefaultValidator {

    /** Validator used to check objects. */
    private final Validator validator;

    /** Creates a validator using the default validator factory. */
    public DefaultValidator() {
        this(Validation.buildDefaultValidatorFactory());
    }

    /**
     * Creates a validator from a factory.
     *
     * @param factory factory supplying the validator
     */
    public DefaultValidator(ValidatorFactory factory) {
        this.validator = factory.getValidator();
    }

    /**
     * Validates a value and throws if any constraints are violated.
     *
     * @param <T> type of the value
     * @param value value to validate
     * @param groups validation groups
     * @throws ConstraintViolationException if validation finds violations
     */
    public <T> void validate(T value, Class<?>... groups) {
        Set<ConstraintViolation<T>> violations = this.validator.validate(value, groups);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

}
