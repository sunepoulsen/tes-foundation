package dk.sunepoulsen.tes.rest.models.testutils

class TestData {

    static List<ExpectedConstraintViolation> createNullAndBankError(String propertyPath) {
        return [
            createError(propertyPath, 'must not be null'),
            createError(propertyPath, 'must not be blank')
        ]
    }

    static ExpectedConstraintViolation createError(String property, String message) {
        return new ExpectedConstraintViolation(
            propertyPath: property,
            message: message
        )
    }

}
