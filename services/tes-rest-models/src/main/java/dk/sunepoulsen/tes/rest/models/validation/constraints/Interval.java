package dk.sunepoulsen.tes.rest.models.validation.constraints;

import dk.sunepoulsen.tes.rest.models.validation.validators.IntervalValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { IntervalValidator.class })
@Documented
public @interface Interval {
    String message() default "max must be equal or greater than min";

    String minProperty();
    String maxProperty();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
