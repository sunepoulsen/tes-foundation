package dk.sunepoulsen.tes.deployment.core.function

import dk.sunepoulsen.tes.deployment.core.data.DeployUser
import dk.sunepoulsen.tes.flows.exceptions.FlowStepException
import spock.lang.Specification

class AtomicDataSupplierSpec extends Specification {

    private AtomicDataSupplier<DeployUser> sut

    void setup() {
        this.sut = new AtomicDataSupplier<>()
    }

    void "Test get null value"() {
        expect:
            sut.get().empty
    }

    void "Test get null value with exception"() {
        when:
            sut.get('Error message')

        then:
            FlowStepException ex = thrown(FlowStepException)
            ex.message == 'Error message'
            ex.cause == null
    }

    void "Test set value and retrieve it"() {
        given:
            DeployUser data = DeployUser.builder()
                .username('user')
                .password('passwd')
                .build()

        and:
            sut.set(data)

        expect:
            sut.get('message') == DeployUser.builder()
                .username('user')
                .password('passwd')
                .build()
    }

}
