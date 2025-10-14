package dk.sunepoulsen.tes.deployment.core.steps

import dk.sunepoulsen.tes.deployment.core.data.DeployCertificate
import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier
import dk.sunepoulsen.tes.security.certificates.DistinguishedName
import dk.sunepoulsen.tes.security.net.ssl.SSLContextFactory
import spock.lang.Specification

import javax.net.ssl.SSLContext

class CreateSelfSignedCertificateStepSpec extends Specification {

    private CreateSelfSignedCertificateStep sut

    void setup() {
        this.sut = new CreateSelfSignedCertificateStep('key')
    }

    void "Test timer name"() {
        expect:
            sut.timerName() == 'key'
    }

    void "Test creation of self signed certificate with success"() {
        given:
            sut.alias = new AtomicDataSupplier<>('alias')
            sut.distinguishedName = new AtomicDataSupplier<>(DistinguishedName.DEFAULT)
            sut.password = new AtomicDataSupplier<>('jukilo90')

        when:
            sut.execute()
            DeployCertificate result = sut.createdCertificate.get("Result has not been set")

        then:
            result.content.length > 0
            result.password == 'jukilo90'

        and:
            SSLContext sslContext = SSLContextFactory.createSSLContext(result.contentAsInputStream, 'jukilo90')
            sslContext.getDefaultSSLParameters().namedGroups.contains('x25519')

    }
}
