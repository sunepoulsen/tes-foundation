package dk.sunepoulsen.tes.deployment.core.steps.factories;

import dk.sunepoulsen.tes.data.generators.CharacterGenerator;
import dk.sunepoulsen.tes.data.generators.DataGenerator;
import dk.sunepoulsen.tes.data.generators.FixedDataGenerator;
import dk.sunepoulsen.tes.data.generators.Generators;
import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier;
import dk.sunepoulsen.tes.deployment.core.steps.DeployCreatePostgresDatabaseScriptStep;
import dk.sunepoulsen.tes.deployment.core.steps.DeployCreateUserStep;
import dk.sunepoulsen.tes.deployment.core.steps.SaveFileContentStep;
import dk.sunepoulsen.tes.templates.VelocityEngineFactory;
import org.apache.velocity.app.VelocityEngine;

import java.nio.file.Path;
import java.util.List;

public class PostgresConfigureStepsFactory {

    private final Path deployDirectory;
    private final VelocityEngine velocityEngine;
    private final DataGenerator<String> passwordGenerator;

    public PostgresConfigureStepsFactory(Path deployDirectory) {
        this.deployDirectory = deployDirectory.getFileSystem().getPath(deployDirectory.toString(), "postgres");
        this.passwordGenerator = Generators.passwordGenerator(CharacterGenerator.ALL_ALPHA_DIGITS);
        this.velocityEngine = VelocityEngineFactory.newInstance();
    }

    public PostgresConfigureStepsDatabaseResult createSteps(String stepKeyPrefix, String databaseName) {
        PostgresConfigureStepsDatabaseResult result = new PostgresConfigureStepsDatabaseResult();
        result.setDatabaseName(databaseName);

        result.setDatabaseAdminUserStep(new DeployCreateUserStep(
            stepKeyPrefix + ".createAdminDatabaseUser",
            new FixedDataGenerator<>(databaseName + "_adm"),
            new FixedDataGenerator<>(passwordGenerator)
        ));

        result.setDatabaseApplicationUserStep(new DeployCreateUserStep(
            stepKeyPrefix + ".createApplicationDatabaseUser",
            new FixedDataGenerator<>(databaseName + "_app"),
            new FixedDataGenerator<>(passwordGenerator)
        ));

        result.setDatabaseScriptStep(new DeployCreatePostgresDatabaseScriptStep(
            stepKeyPrefix + ".createDatabaseScript",
            velocityEngine
        ));
        result.getDatabaseScriptStep().setFilename(databaseName + "-create-database.sh");
        result.getDatabaseScriptStep().setMasterUsername(Generators.fixedGenerator("postgres"));
        result.getDatabaseScriptStep().setDatabaseName(Generators.fixedGenerator(databaseName));
        result.getDatabaseScriptStep().setDatabaseEncoding(Generators.fixedGenerator("UTF8"));
        result.getDatabaseScriptStep().setSchemaNames(Generators.fixedGenerator(List.of(databaseName)));
        result.getDatabaseScriptStep().setAdminUser(result.getDatabaseAdminUserStep().getCreatedUser());
        result.getDatabaseScriptStep().setApplicationUser(result.getDatabaseApplicationUserStep().getCreatedUser());

        result.setSaveDatabaseScriptStep(new SaveFileContentStep(stepKeyPrefix + ".saveDatabaseScript"));
        result.getSaveDatabaseScriptStep().setDirectory(new AtomicDataSupplier<>(deployDirectory));
        result.getSaveDatabaseScriptStep().setFileContent(result.getDatabaseScriptStep().getCreatedScript());

        return result;
    }

}
