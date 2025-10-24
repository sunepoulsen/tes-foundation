package dk.sunepoulsen.tes.security.certificates;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

import dk.sunepoulsen.tes.security.exceptions.SelfSignedCertificateFactoryException;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

@Slf4j
public class SelfSignedCertificateFactory {

    public byte[] createCertificate(String alias, DistinguishedName distinguishedName, String password) throws SelfSignedCertificateFactoryException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            createCertificate(alias, byteArrayOutputStream, distinguishedName, password);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException exception) {
            throw new SelfSignedCertificateFactoryException("Unable to create byte array for self-signed certificate", exception);
        }
    }

    public void createCertificate(String alias, OutputStream outputStream, DistinguishedName distinguishedName, String password) throws SelfSignedCertificateFactoryException {
        try {
            Security.addProvider(new BouncyCastleProvider());

            // Generate Key Pair
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(2048, new SecureRandom());
            KeyPair keyPair = keyPairGen.generateKeyPair();

            // Certificate details
            long now = System.currentTimeMillis();
            Date startDate = new Date(now);

            X500Name dnName = new X500Name(distinguishedName.name());
            BigInteger certSerialNumber = new BigInteger(Long.toString(now));
            Date endDate = new Date(now + 365L * 24 * 60 * 60 * 1000); // 1 year

            // Define SANs
            GeneralName[] names = new GeneralName[] {
                new GeneralName(GeneralName.dNSName, "localhost"),
                new GeneralName(GeneralName.iPAddress, "127.0.0.1")
            };
            GeneralNames subjectAltNames = new GeneralNames(names);

            // Sign the certificate
            ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256WithRSA").build(keyPair.getPrivate());

            X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                dnName, certSerialNumber, startDate, endDate, dnName, keyPair.getPublic());
            certBuilder.addExtension(Extension.subjectAlternativeName, false, subjectAltNames);

            X509Certificate certificate = new JcaX509CertificateConverter()
                .setProvider("BC").getCertificate(certBuilder.build(contentSigner));

            // Store in KeyStore
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(null, null);
            keyStore.setKeyEntry(alias, keyPair.getPrivate(), password.toCharArray(),
                new java.security.cert.Certificate[]{certificate});

            keyStore.store(outputStream, password.toCharArray());
        } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException | OperatorCreationException exception) {
            throw new SelfSignedCertificateFactoryException("Unable to construct self signed certificate", exception);
        }
    }

}
