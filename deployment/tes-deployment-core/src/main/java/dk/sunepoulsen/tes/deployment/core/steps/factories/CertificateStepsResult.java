package dk.sunepoulsen.tes.deployment.core.steps.factories;

import dk.sunepoulsen.tes.deployment.core.steps.CreateSelfSignedCertificateStep;
import dk.sunepoulsen.tes.deployment.core.steps.DeployCreatePostgresDatabaseScriptStep;
import dk.sunepoulsen.tes.deployment.core.steps.DeployCreateUserStep;
import dk.sunepoulsen.tes.deployment.core.steps.SaveFileContentStep;
import dk.sunepoulsen.tes.flows.FlowStep;
import lombok.Data;

import java.util.List;

@Data
public class CertificateStepsResult implements AbstractStepsResult {
    private CreateSelfSignedCertificateStep certificateStep;
    private SaveFileContentStep saveCertificateStep;

    @Override
    public List<FlowStep> steps() {
        return List.of(
            certificateStep,
            saveCertificateStep
        );
    }
}
