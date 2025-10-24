package dk.sunepoulsen.tes.deployment.core.steps.factories;

import dk.sunepoulsen.tes.deployment.core.steps.CreateTemplateFileStep;
import dk.sunepoulsen.tes.deployment.core.steps.SaveFileContentStep;
import dk.sunepoulsen.tes.flows.FlowStep;
import lombok.Data;

import java.util.List;

@Data
public class ConfigurationFileStepsResult implements AbstractStepsResult {
    private CreateTemplateFileStep templateFileStep;
    private SaveFileContentStep saveConfigurationStep;

    @Override
    public List<FlowStep> steps() {
        return List.of(
            templateFileStep,
            saveConfigurationStep
        );
    }
}
