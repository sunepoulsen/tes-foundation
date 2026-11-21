package dk.sunepoulsen.tes.deployment.core.steps;

import dk.sunepoulsen.tes.deployment.core.data.DeployCertificate;
import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier;
import dk.sunepoulsen.tes.flows.FlowStepResult;
import dk.sunepoulsen.tes.flows.exceptions.FlowStepException;
import dk.sunepoulsen.tes.security.certificates.DistinguishedName;
import dk.sunepoulsen.tes.security.certificates.SelfSignedCertificateFactory;
import dk.sunepoulsen.tes.security.exceptions.SelfSignedCertificateFactoryException;
import lombok.Getter;
import lombok.Setter;

/** Creates a self-signed certificate and exposes its content. */
public class CreateSelfSignedCertificateStep extends AbstractDeployStep {

    /** Receives the created certificate. */
    @Getter
    private final AtomicDataSupplier<DeployCertificate> createdCertificate;

    /** Filename assigned to the certificate. */
    @Getter
    @Setter
    private String filename;

    /** Supplies the certificate alias. */
    @Getter
    @Setter
    private AtomicDataSupplier<String> alias;

    /** Supplies the certificate password. */
    @Getter
    @Setter
    private AtomicDataSupplier<String> password;

    /** Supplies the certificate distinguished name. */
    @Getter
    @Setter
    private AtomicDataSupplier<DistinguishedName> distinguishedName;

    /**
     * Creates a certificate step.
     * @param key step and timer key
     */
    public CreateSelfSignedCertificateStep(String key) {
        super(key);
        this.createdCertificate = new AtomicDataSupplier<>();
    }

    /**
     * Creates the certificate from the configured values.
     * @return the successful step result
     * @throws FlowStepException if a supplied value is absent or certificate creation fails
     */
    @Override
    public FlowStepResult execute() {
        try {
            String actualPassword = password.get("Password has not been set");

            SelfSignedCertificateFactory factory = new SelfSignedCertificateFactory();
            byte[] actualCertificate = factory.createCertificate(
                alias.get("Alias has not been set"),
                distinguishedName.get("Distinguished name has not been set"),
                actualPassword
            );

            this.createdCertificate.set(new DeployCertificate(filename, actualCertificate, actualPassword));

            return FlowStepResult.OK;
        } catch (SelfSignedCertificateFactoryException ex) {
            throw new FlowStepException("Could not create self-signed certificate", ex);
        }
    }

}
