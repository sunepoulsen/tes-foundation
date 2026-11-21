package dk.sunepoulsen.tes.deployment.core.steps.factories;

import dk.sunepoulsen.tes.deployment.core.steps.CreateTemplateFileStep;
import dk.sunepoulsen.tes.deployment.core.steps.SaveFileContentStep;
import dk.sunepoulsen.tes.flows.FlowStep;
import lombok.Data;

import java.util.List;

/** Holds the configuration rendering and saving steps. */
@Data
public class ConfigurationFileStepsResult implements AbstractStepsResult {
    /** Step that renders the configuration. */
    private CreateTemplateFileStep templateFileStep;
    /** Step that saves the configuration. */
    private SaveFileContentStep saveConfigurationStep;

    @Override
    public List<FlowStep> steps() {
        return List.of(
            templateFileStep,
            saveConfigurationStep
        );
    }
}
