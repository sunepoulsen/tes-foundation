package dk.sunepoulsen.tes.validation.tests;

import jakarta.validation.ConstraintViolation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

/** Assertions for comparing constraint violations with expected values. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConstraintViolationAssertions {

    /**
     * Sorts and compares violation counts, paths and messages using Java assertions.
     * Paths and messages are compared ignoring case; assertions must be enabled.
     * @param violations actual violations
     * @param expected expected paths and messages
     * @throws AssertionError if an enabled assertion fails
     */
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
