package dk.sunepoulsen.tes.rest.models.validation.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
@Pattern(regexp = UriPathPattern.URI_PATTERN)
public @interface UriPathPattern {
    String URI_PATTERN = "^[^; ]+$";

    String message() default "may not contain characters that can not be encoded in an URI";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
