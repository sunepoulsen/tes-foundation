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

public class CreateSelfSignedCertificateStep extends AbstractDeployStep {

    @Getter
    private final AtomicDataSupplier<DeployCertificate> createdCertificate;

    @Getter
    @Setter
    private String filename;

    @Getter
    @Setter
    private AtomicDataSupplier<String> alias;

    @Getter
    @Setter
    private AtomicDataSupplier<String> password;

    @Getter
    @Setter
    private AtomicDataSupplier<DistinguishedName> distinguishedName;

    public CreateSelfSignedCertificateStep(String key) {
        super(key);
        this.createdCertificate = new AtomicDataSupplier<>();
    }

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
