package dk.sunepoulsen.tes.deployment.core.steps.factories;

import dk.sunepoulsen.tes.data.generators.CharacterGenerator;
import dk.sunepoulsen.tes.data.generators.DataGenerator;
import dk.sunepoulsen.tes.data.generators.FixedDataGenerator;
import dk.sunepoulsen.tes.data.generators.Generators;
import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier;
import dk.sunepoulsen.tes.deployment.core.steps.CreateSelfSignedCertificateStep;
import dk.sunepoulsen.tes.deployment.core.steps.DeployCreatePostgresDatabaseScriptStep;
import dk.sunepoulsen.tes.deployment.core.steps.DeployCreateUserStep;
import dk.sunepoulsen.tes.deployment.core.steps.SaveFileContentStep;
import dk.sunepoulsen.tes.security.certificates.DistinguishedName;
import dk.sunepoulsen.tes.templates.VelocityEngineFactory;
import org.apache.velocity.app.VelocityEngine;

import java.nio.file.Path;
import java.util.List;

public class CertificateStepsFactory {

    private final Path storeDirectory;
    private final DataGenerator<String> passwordGenerator;

    public CertificateStepsFactory(Path storeDirectory) {
        this.storeDirectory = storeDirectory;
        this.passwordGenerator = Generators.passwordGenerator(CharacterGenerator.ALL_ALPHA_DIGITS);
    }

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
