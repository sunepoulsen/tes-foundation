package dk.sunepoulsen.tes.deployment.core.steps.factories;

import dk.sunepoulsen.tes.data.generators.CharacterGenerator;
import dk.sunepoulsen.tes.data.generators.DataGenerator;
import dk.sunepoulsen.tes.data.generators.Generators;
import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier;
import dk.sunepoulsen.tes.deployment.core.steps.CreateSelfSignedCertificateStep;
import dk.sunepoulsen.tes.deployment.core.steps.SaveFileContentStep;
import dk.sunepoulsen.tes.security.certificates.DistinguishedName;

import java.nio.file.Path;

/** Creates steps to generate and save a self-signed certificate. */
public class CertificateStepsFactory {

    /** Destination directory for the certificate. */
    private final Path storeDirectory;
    /** Generator for certificate passwords. */
    private final DataGenerator<String> passwordGenerator;

    /**
     * Creates a certificate steps factory.
     * @param storeDirectory directory in which to save the certificate
     */
    public CertificateStepsFactory(Path storeDirectory) {
        this.storeDirectory = storeDirectory;
        this.passwordGenerator = Generators.passwordGenerator(CharacterGenerator.ALL_ALPHA_DIGITS);
    }

    /**
     * Configures linked certificate creation and saving steps with a generated password.
     * @param stepKeyPrefix prefix for step keys
     * @return the configured steps
     */
    public CertificateStepsResult createSteps(String stepKeyPrefix) {
        CertificateStepsResult result = new CertificateStepsResult();

        result.setCertificateStep(new CreateSelfSignedCertificateStep(stepKeyPrefix + ".createCertificate"));
        result.getCertificateStep().setFilename("tes-foundation.p12");
        result.getCertificateStep().setAlias(new AtomicDataSupplier<>("tes"));
        result.getCertificateStep().setPassword(new AtomicDataSupplier<>(passwordGenerator.generate()));
        result.getCertificateStep().setDistinguishedName(new AtomicDataSupplier<>(DistinguishedName.DEFAULT));

        result.setSaveCertificateStep(new SaveFileContentStep(stepKeyPrefix + ".saveCertificate"));
        result.getSaveCertificateStep().setDirectory(new AtomicDataSupplier<>(storeDirectory));
        result.getSaveCertificateStep().setFileContent(result.getCertificateStep().getCreatedCertificate());

        return result;
    }

}
