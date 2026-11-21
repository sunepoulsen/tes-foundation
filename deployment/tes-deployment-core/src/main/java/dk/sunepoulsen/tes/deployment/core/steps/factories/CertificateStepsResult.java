package dk.sunepoulsen.tes.deployment.core.steps.factories;

import dk.sunepoulsen.tes.deployment.core.steps.CreateSelfSignedCertificateStep;
import dk.sunepoulsen.tes.deployment.core.steps.SaveFileContentStep;
import dk.sunepoulsen.tes.flows.FlowStep;
import lombok.Data;

import java.util.List;

/** Holds the certificate creation and saving steps. */
@Data
public class CertificateStepsResult implements AbstractStepsResult {
    /** Step that creates the certificate. */
    private CreateSelfSignedCertificateStep certificateStep;
    /** Step that saves the certificate. */
    private SaveFileContentStep saveCertificateStep;

    @Override
    public List<FlowStep> steps() {
        return List.of(
            certificateStep,
            saveCertificateStep
        );
    }
}
