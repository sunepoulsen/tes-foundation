package dk.sunepoulsen.tes.deployment.core.steps.factories;

import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier;
import dk.sunepoulsen.tes.deployment.core.steps.CreateTemplateFileStep;
import dk.sunepoulsen.tes.deployment.core.steps.SaveFileContentStep;
import dk.sunepoulsen.tes.templates.VelocityEngineFactory;
import lombok.Getter;
import org.apache.velocity.app.VelocityEngine;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/** Creates steps to render and save a configuration file. */
public class ConfigurationFileStepsFactory {

    /** Destination directory for the configuration file. */
    private final Path storeDirectory;
    /** Template used to render the configuration. */
    private final String templateName;
    /** Filename assigned to the rendered configuration. */
    private final String filename;
    /** Engine used to render the template. */
    private final VelocityEngine velocityEngine;

    /** Named suppliers of template context values. */
    @Getter
    private final Map<String, AtomicDataSupplier<?>> contextSupplier;

    /**
     * Creates a configuration steps factory.
     * @param templateName template to render
     * @param storeDirectory directory in which to save the file
     * @param filename filename for the rendered configuration
     */
    public ConfigurationFileStepsFactory(final String templateName, Path storeDirectory, final String filename) {
        this.templateName = templateName;
        this.storeDirectory = storeDirectory;
        this.filename = filename;
        this.velocityEngine = VelocityEngineFactory.newInstance();
        this.contextSupplier = new HashMap<>();
    }

    /** Adds the default service log file path to the template context. */
    public void addDefaultTesServiceContext() {
        this.contextSupplier.put("serviceLogFile", new AtomicDataSupplier<>("/app/logs/service.log"));
    }

    /**
     * Adds the certificate path and password suppliers to the template context.
     * @param certificateStepsResult result containing the configured certificate step
     */
    public void addCertificateContext(CertificateStepsResult certificateStepsResult) {
        this.contextSupplier.put("certificateFile", new AtomicDataSupplier<>("/app/certificates/" + certificateStepsResult.getCertificateStep().getFilename()));
        this.contextSupplier.put("certificatePassword", certificateStepsResult.getCertificateStep().getPassword());
    }

    /**
     * Configures linked template rendering and file saving steps.
     * @param stepKeyPrefix prefix for step keys
     * @return the configured steps
     */
    public ConfigurationFileStepsResult createSteps(String stepKeyPrefix) {
        ConfigurationFileStepsResult result = new ConfigurationFileStepsResult();

        result.setTemplateFileStep(new CreateTemplateFileStep(stepKeyPrefix + ".createConfigurationFile", velocityEngine, templateName));
        result.getTemplateFileStep().setFilename(filename);
        contextSupplier.forEach((k, v) ->
            result.getTemplateFileStep().addContextSupplier(k, v)
        );

        result.setSaveConfigurationStep(new SaveFileContentStep(stepKeyPrefix + ".saveConfigurationFile"));
        result.getSaveConfigurationStep().setDirectory(new AtomicDataSupplier<>(storeDirectory));
        result.getSaveConfigurationStep().setFileContent(result.getTemplateFileStep().getFileContent());

        return result;
    }

}
