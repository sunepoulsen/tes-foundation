package dk.sunepoulsen.tes.security.net.ssl

import com.google.common.jimfs.Configuration
import com.google.common.jimfs.Jimfs
import dk.sunepoulsen.tes.security.certificates.DistinguishedName
import dk.sunepoulsen.tes.security.certificates.SelfSignedCertificateFactory
import dk.sunepoulsen.tes.security.exceptions.SSLContextFactoryException
import spock.lang.Specification

import javax.net.ssl.SSLContext
import java.nio.file.FileSystem
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

class SSLContextFactorySpec extends Specification {

    void "Test construction of SSLContext from certificate inputStream with success"() {
        given:
            String password = '12345678'

        and:
            byte[] generatedCertificate = new SelfSignedCertificateFactory().createCertificate(DistinguishedName.DEFAULT.commonName, DistinguishedName.DEFAULT, password)

        when:
            SSLContext result = SSLContextFactory.createSSLContext(new ByteArrayInputStream(generatedCertificate), password)

        then:
            result != null
            result.getDefaultSSLParameters().namedGroups == [
                'x25519', 'secp256r1', 'secp384r1', 'secp521r1', 'x448',
                'ffdhe2048', 'ffdhe3072', 'ffdhe4096', 'ffdhe6144', 'ffdhe8192'
            ].toArray()
    }

    void "Test construction of SSLContext from certificate inputStream with bad password"() {
        given:
            String password = '12345678'

        and:
            byte[] generatedCertificate = new SelfSignedCertificateFactory().createCertificate(DistinguishedName.DEFAULT.commonName, DistinguishedName.DEFAULT, password)

        when:
            SSLContextFactory.createSSLContext(new ByteArrayInputStream(generatedCertificate), 'bad-password')

        then:
            SSLContextFactoryException exception = thrown(SSLContextFactoryException)
            exception.message == 'Unable to construct SSLContext'
            exception.cause instanceof IOException
            exception.cause.message == 'keystore password was incorrect'
    }

    void "Test construction of SSLContext from certificate inputStream with bad content"() {
        given:
            String password = '12345678'

        and:
            byte[] generatedCertificate = "bad-certificate-content".bytes

        when:
            SSLContextFactory.createSSLContext(new ByteArrayInputStream(generatedCertificate), password)

        then:
            SSLContextFactoryException exception = thrown(SSLContextFactoryException)
            exception.message == 'Unable to construct SSLContext'
            exception.cause instanceof EOFException
            exception.cause.message == null
    }

    void "Test construction of SSLContext from certificate file with success"() {
        given:
            String password = '12345678'

        and:
            byte[] generatedCertificate = new SelfSignedCertificateFactory().createCertificate(DistinguishedName.DEFAULT.commonName, DistinguishedName.DEFAULT, password)

        and:
            FileSystem fs = Jimfs.newFileSystem(Configuration.forCurrentPlatform())
            Path certificatePath = fs.getPath('certificate-file.pkcs12')
            Files.write(certificatePath, generatedCertificate, StandardOpenOption.CREATE_NEW)

        when:
            SSLContext result = SSLContextFactory.createSSLContext(certificatePath, password)

        then:
            result != null
            result.getDefaultSSLParameters().namedGroups == [
                'x25519', 'secp256r1', 'secp384r1', 'secp521r1', 'x448',
                'ffdhe2048', 'ffdhe3072', 'ffdhe4096', 'ffdhe6144', 'ffdhe8192'
            ].toArray()
    }

    void "Test construction of SSLContext from certificate file that does not exist"() {
        given:
            FileSystem fs = Jimfs.newFileSystem(Configuration.forCurrentPlatform())
            Path certificatePath = fs.getPath('path-does-not-exist')

        when:
            SSLContextFactory.createSSLContext(certificatePath, 'passwd')

        then:
            SSLContextFactoryException exception = thrown(SSLContextFactoryException)
            exception.message == "The certificate file '${certificatePath.toAbsolutePath()}' does not exist"
    }

}
