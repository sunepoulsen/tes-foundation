package dk.sunepoulsen.tes.deployment.core.steps.factories;

import dk.sunepoulsen.tes.deployment.core.steps.DeployCreatePostgresDatabaseScriptStep;
import dk.sunepoulsen.tes.deployment.core.steps.DeployCreateUserStep;
import dk.sunepoulsen.tes.deployment.core.steps.SaveFileContentStep;
import dk.sunepoulsen.tes.flows.FlowStep;
import lombok.Data;

import java.util.List;

@Data
public class PostgresConfigureStepsDatabaseResult implements AbstractStepsResult {
    private String databaseName;
    private DeployCreateUserStep databaseAdminUserStep;
    private DeployCreateUserStep databaseApplicationUserStep;
    private DeployCreatePostgresDatabaseScriptStep databaseScriptStep;
    private SaveFileContentStep saveDatabaseScriptStep;

    @Override
    public List<FlowStep> steps() {
        return List.of(
            databaseAdminUserStep,
            databaseApplicationUserStep,
            databaseScriptStep,
            saveDatabaseScriptStep
        );
    }
}
