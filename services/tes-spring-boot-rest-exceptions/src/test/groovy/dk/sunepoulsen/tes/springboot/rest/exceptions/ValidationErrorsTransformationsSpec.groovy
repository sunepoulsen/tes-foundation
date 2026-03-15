package dk.sunepoulsen.tes.springboot.rest.exceptions

import dk.sunepoulsen.tes.rest.models.RangeModel
import dk.sunepoulsen.tes.rest.models.ServiceValidationError
import dk.sunepoulsen.tes.rest.models.validation.DefaultValidator
import jakarta.validation.ConstraintViolationException
import org.springframework.validation.FieldError
import spock.lang.Specification

class ValidationErrorsTransformationsSpec extends Specification {

    private DefaultValidator validator
    private ValidationErrorsTransformations sut

    void setup() {
        this.validator = new DefaultValidator()
        this.sut = new ValidationErrorsTransformations()
    }

    void "Tests transformation of ConstraintViolationException to ServiceValidationError"() {
        given:
            RangeModel<Integer> model = new RangeModel(
                min: -10,
                max: -5
            )

        when:
            List<ServiceValidationError> result = []
            try {
                this.validator.validate(model)
            } catch (ConstraintViolationException ex) {
                result = sut.toServiceValidationErrors(ex)
            }

        then:
            result == [
                new ServiceValidationError(
                    param: 'max',
                    message: 'must be greater than or equal to 0'
                ),
                new ServiceValidationError(
                    param: 'min',
                    message: 'must be greater than or equal to 0'
                ),
            ]
    }

    void "Tests transformation of FieldError's to ServiceValidationError"() {
        given:
            List<FieldError> errors = [
                new FieldError('objectName1', 'property1.property2', 'message 1'),
                new FieldError('objectName1', 'property1.property3', 'message 2'),
                new FieldError('objectName2', 'property1', 'message 3'),
                new FieldError('objectName2', 'property3.property4', 'message 4')
            ]

        expect:
            sut.toServiceValidationErrors(errors) == [
                new ServiceValidationError(
                    param: errors[2].field,
                    message: errors[2].getDefaultMessage()
                ),
                new ServiceValidationError(
                    param: errors[0].field,
                    message: errors[0].getDefaultMessage()
                ),
                new ServiceValidationError(
                    param: errors[1].field,
                    message: errors[1].getDefaultMessage()
                ),
                new ServiceValidationError(
                    param: errors[3].field,
                    message: errors[3].getDefaultMessage()
                ),
            ]
    }
}
