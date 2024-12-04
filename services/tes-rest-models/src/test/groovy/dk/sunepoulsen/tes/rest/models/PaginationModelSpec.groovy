package dk.sunepoulsen.tes.rest.models

import dk.sunepoulsen.tes.rest.models.testutils.ConstraintViolationAssertions
import dk.sunepoulsen.tes.rest.models.testutils.TestData
import dk.sunepoulsen.tes.rest.models.validation.DefaultValidator
import jakarta.validation.ConstraintViolationException
import spock.lang.Specification
import spock.lang.Unroll

class PaginationModelSpec extends Specification {

    private DefaultValidator validator

    void setup() {
        this.validator = new DefaultValidator()
    }

    void "Validate a valid PaginationModel"() {
        expect:
            this.validator.validate(new PaginationModel<String>(
                metadata: new PaginationMetaData(
                    page: 0,
                    totalPages: 1,
                    totalItems: 15,
                    size: 15
                ),
                results: []
            ))
    }

    void "Validate an invalid PaginationModel where the 'results' property is null"() {
        when:
            this.validator.validate(new PaginationModel<String>(
                metadata: new PaginationMetaData(
                    page: 0,
                    totalPages: 1,
                    totalItems: 15,
                    size: 15
                )
            ))

        then:
            ConstraintViolationException exception = thrown(ConstraintViolationException)
            ConstraintViolationAssertions.verifyViolations(exception.constraintViolations, [
                TestData.createError('results', 'must not be null')
            ])
    }

    @Unroll
    void "Validate an invalid ServiceInfo: #_testcase"() {
        when:
            this.validator.validate(new PaginationModel<String>(
                metadata: new PaginationMetaData(
                    page: _page,
                    totalPages: _totalPages,
                    totalItems: _totalItems,
                    size: _size
                ),
                results: []
            ))

        then:
            ConstraintViolationException exception = thrown(ConstraintViolationException)
            ConstraintViolationAssertions.verifyViolations(exception.constraintViolations, _errors)

        where:
            _testcase                         | _page | _totalPages | _totalItems | _size | _errors
            'metadata.page is negative'       | -4    | 1           | 15          | 15    | [TestData.createError('metadata.page', 'must be greater than or equal to 0')]
            'metadata.totalPages is negative' | 0     | -1          | 15          | 15    | [TestData.createError('metadata.totalPages', 'must be greater than or equal to 0')]
            'metadata.totalItems is negative' | 0     | 1           | -15         | 15    | [TestData.createError('metadata.totalItems', 'must be greater than or equal to 0')]
            'metadata.size is negative'       | 0     | 1           | 15          | -15   | [TestData.createError('metadata.size', 'must be greater than or equal to 0')]

    }

    void "Validate an PaginationModel with invalid result items"() {
        when:
            this.validator.validate(new PaginationModel<RangeModel<Integer>>(
                metadata: new PaginationMetaData(
                    page: 0,
                    totalPages: 1,
                    totalItems: 15,
                    size: 15
                ),
                results: [new RangeModel<Integer>(
                    min: -18,
                    max: 24
                )]
            ))

        then:
            ConstraintViolationException exception = thrown(ConstraintViolationException)
            ConstraintViolationAssertions.verifyViolations(exception.constraintViolations, [
                TestData.createError('results[0].min', 'must be greater than or equal to 0')
            ])
    }
}