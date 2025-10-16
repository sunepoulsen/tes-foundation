package dk.sunepoulsen.tes.sut.engine.steps.factories;

import dk.sunepoulsen.tes.data.generators.CharacterGenerator;
import dk.sunepoulsen.tes.data.generators.DataGenerator;
import dk.sunepoulsen.tes.data.generators.Generators;
import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier;
import dk.sunepoulsen.tes.deployment.core.steps.factories.PostgresConfigureStepsDatabasesResult;
import dk.sunepoulsen.tes.io.resources.PropertiesResource;
import dk.sunepoulsen.tes.io.resources.ResourceException;
import dk.sunepoulsen.tes.sut.engine.steps.SutCreateTestContainerNetworkStep;
import dk.sunepoulsen.tes.sut.engine.steps.SutStartPostgresStep;
import dk.sunepoulsen.tes.sut.engine.system.SystemUnderTestDeployment;

import java.nio.file.Path;
import java.util.List;

public class PostgresContainerStepsFactory {

    private final PropertiesResource propertiesResource;
    private final SystemUnderTestDeployment systemUnderTestDeployment;
    private final SutCreateTestContainerNetworkStep createTestContainerNetworkStep;
    private final DataGenerator<String> passwordGenerator;
    private final Path logDirectory;

    public PostgresContainerStepsFactory(SystemUnderTestDeployment systemUnderTestDeployment, SutCreateTestContainerNetworkStep createTestContainerNetworkStep, Path logDirectory) throws ResourceException {
        this.propertiesResource = new PropertiesResource(getClass().getResourceAsStream("/sut-engine.properties"));
        this.systemUnderTestDeployment = systemUnderTestDeployment;
        this.createTestContainerNetworkStep = createTestContainerNetworkStep;
        this.passwordGenerator = Generators.passwordGenerator(CharacterGenerator.ALL_ALPHA_DIGITS);
        this.logDirectory = logDirectory;
    }

    public ContainerStepResult<SutStartPostgresStep> createSteps(PostgresConfigureStepsDatabasesResult databasesResult) {
        SutStartPostgresStep postgresContainerStep = new SutStartPostgresStep("startDatabaseContainer", propertiesResource.getProperty("postgres.service.key"), systemUnderTestDeployment);
        postgresContainerStep.setDockerImageName(new AtomicDataSupplier<>(propertiesResource.getProperty("postgres.docker.image")));
        postgresContainerStep.setDockerImageTag(new AtomicDataSupplier<>(propertiesResource.getProperty("postgres.docker.tag")));
        postgresContainerStep.setNetwork(createTestContainerNetworkStep.getCreatedNetwork());
        postgresContainerStep.setMasterUsername(new AtomicDataSupplier<>(databasesResult.getDatabases().getFirst().getDatabaseScriptStep().getMasterUsername().generate()));
        postgresContainerStep.setMasterPassword(new AtomicDataSupplier<>(passwordGenerator.generate()));
        postgresContainerStep.setAliases(List.of(new AtomicDataSupplier<>(propertiesResource.getProperty("postgres.network.alias"))));
        postgresContainerStep.setStartupScripts(databasesResult.getDatabases().stream()
            .map(databaseResult -> databaseResult.getSaveDatabaseScriptStep().getCreatedPath())
            .toList()
        );
        postgresContainerStep.setLogPath(new AtomicDataSupplier<>(logDirectory));

        return new ContainerStepResult<>(postgresContainerStep);
    }
}
