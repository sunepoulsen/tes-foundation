package dk.sunepoulsen.tes.validation.tests;

public record ExpectedConstraintViolation(
    String propertyPath,
    String message
)
{
}
