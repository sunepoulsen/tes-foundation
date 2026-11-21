package dk.sunepoulsen.tes.validation.tests;

/**
 * Expected property path and message of a constraint violation.
 * @param propertyPath path of the violated property
 * @param message expected violation message
 */
public record ExpectedConstraintViolation(
    String propertyPath,
    String message
)
{
}
