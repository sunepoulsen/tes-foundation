package dk.sunepoulsen.tes.rest.models.monitoring

import dk.sunepoulsen.tes.rest.models.testutils.ConstraintViolationAssertions
import dk.sunepoulsen.tes.rest.models.testutils.TestData
import dk.sunepoulsen.tes.rest.models.validation.DefaultValidator
import jakarta.validation.ConstraintViolationException
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Test of the validation of {@cpde ServiceInfo}
 */
class ServiceInfoSpec extends Specification {

    private DefaultValidator validator

    void setup() {
        this.validator = new DefaultValidator()
    }

    void "Validate a valid ServiceInfo"() {
        expect:
            this.validator.validate(new ServiceInfo(
                app: new ServiceInfoApp(
                    name: 'name',
                    version: '1.0.0',
                    service: new ServiceInfoService(
                        name: 'service name'
                    )
                )
            ))
    }

    void "Validate an invalid ServiceInfo where the 'app' property is null"() {
        when:
            this.validator.validate(new ServiceInfo())

        then:
            ConstraintViolationException exception = thrown(ConstraintViolationException)
            ConstraintViolationAssertions.verifyViolations(exception.constraintViolations, [
                TestData.createError('app', 'must not be null')
            ])
    }

    void "Validate an invalid ServiceInfo where the 'app.service' property is null"() {
        when:
            this.validator.validate(new ServiceInfo(
                app: new ServiceInfoApp(
                    name: 'name',
                    version: '1.0.0'
                )))

        then:
            ConstraintViolationException exception = thrown(ConstraintViolationException)
            ConstraintViolationAssertions.verifyViolations(exception.constraintViolations, [
                TestData.createError('app.service', 'must not be null')
            ])
    }

    @Unroll
    void "Validate an invalid ServiceInfo: #_testcase"() {
        when:
            this.validator.validate(new ServiceInfo(
                app: new ServiceInfoApp(
                    name: _appName,
                    version: _version,
                    service: new ServiceInfoService(
                        name: _serviceName
                    )
                )
            ))

        then:
            ConstraintViolationException exception = thrown(ConstraintViolationException)
            ConstraintViolationAssertions.verifyViolations(exception.constraintViolations, _errors)

        where:
            _testcase                   | _appName | _version | _serviceName   | _errors
            'app.name is null'          | null     | '1.0.0'  | 'service name' | TestData.createNullAndBankError('app.name')
            'app.name is blank'         | ''       | '1.0.0'  | 'service name' | [TestData.createError('app.name', 'must not be blank')]
            'app.version is null'       | 'name'   | null     | 'service name' | TestData.createNullAndBankError('app.version')
            'app.version is blank'      | 'name'   | ''       | 'service name' | [TestData.createError('app.version', 'must not be blank')]
            'app.service.name is null'  | 'name'   | '1.0.0'  | null           | TestData.createNullAndBankError('app.service.name')
            'app.service.name is blank' | 'name'   | '1.0.0'  | ''             | [TestData.createError('app.service.name', 'must not be blank')]
    }

}
