package dk.sunepoulsen.tes.security.certificates


import spock.lang.Specification

import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.TrustManagerFactory
import java.security.KeyStore

class SelfSignedCertificateFactorySpec extends Specification {

    void "Tests construction of PKCS12 Self signed certificate"() {
        given:
            String password = '12345678'

        when:
            byte[] generatedCertificate = new SelfSignedCertificateFactory().createCertificate(DistinguishedName.DEFAULT.commonName, DistinguishedName.DEFAULT, password)

        then:
            // Load PKCS12 certificate bytes
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(new ByteArrayInputStream(generatedCertificate), password.toCharArray());

            // Initialize KeyManagerFactory with the KeyStore
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, password.toCharArray());

            // Initialize TrustManagerFactory with the KeyStore
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
    }

}
