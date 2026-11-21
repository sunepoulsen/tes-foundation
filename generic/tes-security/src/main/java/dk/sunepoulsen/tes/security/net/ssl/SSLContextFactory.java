package dk.sunepoulsen.tes.security.net.ssl;

import dk.sunepoulsen.tes.security.exceptions.SSLContextFactoryException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.*;
import java.security.cert.CertificateException;

/** Creates TLS contexts from PKCS12 key stores. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SSLContextFactory {

    /**
     * Loads a PKCS12 file and creates a TLS context using it for keys and trust.
     * @param certificateFile path to the key store
     * @param password key store and key password
     * @return the initialized TLS context
     * @throws SSLContextFactoryException if the file or context cannot be loaded
     */
    public static SSLContext createSSLContext(Path certificateFile, String password) throws SSLContextFactoryException {
        if (!Files.exists(certificateFile)) {
            throw new SSLContextFactoryException("The certificate file '" + certificateFile.toAbsolutePath() + "' does not exist");
        }

        try(InputStream inputStream = Files.newInputStream(certificateFile, StandardOpenOption.READ)) {
            return createSSLContext(inputStream, password);
        } catch (IOException ex) {
            throw new SSLContextFactoryException("Unable to read the certificate file '" + certificateFile.toAbsolutePath() + "'", ex);
        }
    }
    /**
     * Loads a PKCS12 stream and creates a TLS context using it for keys and trust.
     * @param certificateInputStream key store stream, which is not closed by this method
     * @param password key store and key password
     * @return the initialized TLS context
     * @throws SSLContextFactoryException if the key store or context cannot be loaded
     */
    public static SSLContext createSSLContext(InputStream certificateInputStream, String password) throws SSLContextFactoryException {
        try {
            // Load PKCS12 certificate file
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(certificateInputStream, password.toCharArray());

            // Initialize KeyManagerFactory with the KeyStore
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, password.toCharArray());

            // Initialize TrustManagerFactory with the KeyStore
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            // Initialize SSLContext with the KeyManagers and TrustManagers
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

            return sslContext;
        } catch (UnrecoverableKeyException | CertificateException | KeyStoreException | IOException | NoSuchAlgorithmException | KeyManagementException ex) {
            throw new SSLContextFactoryException("Unable to construct SSLContext", ex);
        }
    }

}
