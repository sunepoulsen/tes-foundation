package dk.sunepoulsen.tes.security.keys

import spock.lang.Specification

import java.security.KeyPair
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

class KeyPairFactorySpec extends Specification {

    void "Create Key Pair with default settings"() {
        when:
            KeyPair keyPair = new KeyPairFactory().createKeyPair()

        then:
            keyPair != null
            keyPair.private instanceof RSAPrivateKey
            keyPair.public instanceof RSAPublicKey
            (keyPair.private as RSAPrivateKey).modulus.bitLength() == KeyPairFactory.DEFAULT_KEY_SIZE
            (keyPair.public as RSAPublicKey).modulus.bitLength() == KeyPairFactory.DEFAULT_KEY_SIZE
    }

    void "Create Key Pair with custom valid settings"() {
        when:
            KeyPair keyPair = new KeyPairFactory().createKeyPair(KeyPairFactory.DEFAULT_KEY_ALGORITHM, KeyPairFactory.DEFAULT_KEY_SIZE)

        then:
            keyPair != null
            keyPair.private instanceof RSAPrivateKey
            keyPair.public instanceof RSAPublicKey
            (keyPair.private as RSAPrivateKey).modulus.bitLength() == KeyPairFactory.DEFAULT_KEY_SIZE
            (keyPair.public as RSAPublicKey).modulus.bitLength() == KeyPairFactory.DEFAULT_KEY_SIZE
    }

    void "Create Key Pair with custom invalid settings"() {
        when:
            new KeyPairFactory().createKeyPair('unknown', KeyPairFactory.DEFAULT_KEY_SIZE)

        then:
            thrown(IllegalStateException)
    }

}
