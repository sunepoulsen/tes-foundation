package dk.sunepoulsen.tes.rest.models.validation.constraints;

import dk.sunepoulsen.tes.rest.models.validation.validators.IntervalValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/** Validates that a maximum property is not less than a minimum property. */
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { IntervalValidator.class })
@Documented
public @interface Interval {
    /** @return validation message */
    String message() default "max must be equal or greater than min";

    /** @return name of the minimum property */
    String minProperty();
    /** @return name of the maximum property */
    String maxProperty();

    /** @return validation groups */
    Class<?>[] groups() default { };

    /** @return validation payload types */
    Class<? extends Payload>[] payload() default { };
}
