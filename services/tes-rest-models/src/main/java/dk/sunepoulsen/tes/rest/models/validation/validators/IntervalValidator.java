package dk.sunepoulsen.tes.rest.models.validation.validators;

import dk.sunepoulsen.tes.rest.models.validation.constraints.Interval;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;

public class IntervalValidator implements ConstraintValidator<Interval, Object> {

    private Interval annotation;

    @Override
    public void initialize(Interval constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        Object minValue;
        Object maxValue;

        minValue = readProperty(obj, annotation.minProperty());
        if (minValue == null) {
            return true;
        }

        maxValue = readProperty(obj, annotation.maxProperty());
        if (maxValue == null) {
            return true;
        }

        Number minNumber = castToNumber(minValue, annotation.minProperty());
        Number maxNumber = castToNumber(maxValue, annotation.maxProperty());

        return minNumber.doubleValue() <= maxNumber.doubleValue();
    }

    private Object readProperty(Object obj, String property) {
        try {
            Field field = obj.getClass().getDeclaredField(property);
            boolean accessibility = field.canAccess(obj);

            field.setAccessible(true);
            Object fieldValue = field.get(obj);
            field.setAccessible(accessibility);

            return fieldValue;
        } catch (NoSuchFieldException ex) {
            throw new IllegalArgumentException("No property '" + property + "' exists", ex);
        } catch (IllegalAccessException ex) {
            throw new IllegalArgumentException("Unable to read property '" + property + "'", ex);
        }
    }

    private Number castToNumber(Object obj, String property) {
        if (obj instanceof Number number) {
            return number;
        }

        throw new IllegalArgumentException("Property '" + property + "' is not a number");
    }

}
