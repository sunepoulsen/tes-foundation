package dk.sunepoulsen.tes.validation.tests;

import jakarta.validation.ConstraintViolation;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ConstraintViolationAssertions {

    public static void verifyViolations(Set<ConstraintViolation<?>> violations, List<ExpectedConstraintViolation> expected) {
        List<ConstraintViolation<?>> actual = violations.stream()
            .sorted(Comparator.comparing(constraintViolation ->
                constraintViolation.getPropertyPath().toString() + ":" + constraintViolation.getMessage()
            ))
            .toList();

        expected = expected.stream()
            .sorted(Comparator.comparing(ExpectedConstraintViolation::propertyPath)
                .thenComparing(ExpectedConstraintViolation::message))
            .toList();

        assert actual.size() == expected.size();
        for (int i = 0; i < expected.size(); i++) {
            assert actual.get(i).getPropertyPath().toString().equalsIgnoreCase(expected.get(i).propertyPath());
            assert actual.get(i).getMessage().equalsIgnoreCase(expected.get(i).message());
        }
    }

}
