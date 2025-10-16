package dk.sunepoulsen.tes.deployment.core.steps.factories;

import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier;
import dk.sunepoulsen.tes.deployment.core.steps.CreateTemplateFileStep;
import dk.sunepoulsen.tes.deployment.core.steps.SaveFileContentStep;
import dk.sunepoulsen.tes.templates.VelocityEngineFactory;
import org.apache.velocity.app.VelocityEngine;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationFileStepsFactory {

    private final Path storeDirectory;
    private final String templateName;
    private final String filename;
    private final VelocityEngine velocityEngine;

    private final Map<String, AtomicDataSupplier<?>> contextSupplier;

    public ConfigurationFileStepsFactory(final String templateName, Path storeDirectory, final String filename) {
        this.templateName = templateName;
        this.storeDirectory = storeDirectory;
        this.filename = filename;
        this.velocityEngine = VelocityEngineFactory.newInstance();
        this.contextSupplier = new HashMap<>();
    }

    public void addDefaultTesServiceContext() {
        this.contextSupplier.put("serviceLogFile", new AtomicDataSupplier<>("/app/logs/service.log"));
    }

    public void addCertificateContext(CertificateStepsResult certificateStepsResult) {
        this.contextSupplier.put("certificateFile", new AtomicDataSupplier<>("/app/certificates/" + certificateStepsResult.getCertificateStep().getFilename()));
        this.contextSupplier.put("certificatePassword", certificateStepsResult.getCertificateStep().getPassword());
    }

    public void addDatabaseContext(AtomicDataSupplier<String> databaseHost, PostgresConfigureStepsDatabaseResult databaseStepsResult) {
        this.contextSupplier.put("databaseHost", databaseHost);
        this.contextSupplier.put("databaseName", new AtomicDataSupplier<>(databaseStepsResult.getDatabaseName()));
        this.contextSupplier.put("databaseAdminUser", databaseStepsResult.getDatabaseAdminUserStep().getCreatedUser());
        this.contextSupplier.put("databaseApplicationUser", databaseStepsResult.getDatabaseApplicationUserStep().getCreatedUser());
    }

    public ConfigurationFileStepsResult createSteps(String stepKeyPrefix) {
        ConfigurationFileStepsResult result = new ConfigurationFileStepsResult();

        result.setTemplateFileStep(new CreateTemplateFileStep(stepKeyPrefix + ".createConfigurationFile", velocityEngine, templateName));
        result.getTemplateFileStep().setFilename(filename);
        contextSupplier.forEach((k, v) -> {
            result.getTemplateFileStep().addContextSupplier(k, v);
        });

        result.setSaveConfigurationStep(new SaveFileContentStep(stepKeyPrefix + ".saveConfigurationFile"));
        result.getSaveConfigurationStep().setDirectory(new AtomicDataSupplier<>(storeDirectory));
        result.getSaveConfigurationStep().setFileContent(result.getTemplateFileStep().getFileContent());

        return result;
    }

}
