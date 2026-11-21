package dk.sunepoulsen.tes.deployment.core.steps;

import dk.sunepoulsen.tes.deployment.core.data.DeployFileContent;
import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier;
import dk.sunepoulsen.tes.flows.FlowStepResult;
import dk.sunepoulsen.tes.flows.exceptions.FlowStepException;
import dk.sunepoulsen.tes.templates.TesTemplate;
import lombok.Getter;
import lombok.Setter;
import org.apache.velocity.app.VelocityEngine;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Supplier;

/** Renders a template into deployment file content. */
public class CreateTemplateFileStep extends AbstractDeployStep {

    /** Receives the rendered file content. */
    @Getter
    @Setter
    private AtomicDataSupplier<DeployFileContent> fileContent;

    /** Filename assigned to the rendered content. */
    @Getter
    @Setter
    private String filename;

    /** Engine used to render the template. */
    private final VelocityEngine velocityEngine;
    /** Name of the template to render. */
    private final String templateName;
    /** Suppliers for named template context values. */
    private final Map<String, AtomicDataSupplier<?>> contextSupplier;

    /**
     * Creates a template rendering step.
     * @param key step and timer key
     * @param velocityEngine engine used to render the template
     * @param templateName name of the template
     */
    public CreateTemplateFileStep(String key, VelocityEngine velocityEngine, String templateName) {
        super(key);
        this.fileContent = new AtomicDataSupplier<>();
        this.velocityEngine = velocityEngine;
        this.templateName = templateName;
        this.contextSupplier = new HashMap<>();
    }

    /**
     * Adds or replaces a named context supplier.
     * @param name context variable name
     * @param supplier supplier of its value
     */
    public void addContextSupplier(String name, AtomicDataSupplier<?> supplier) {
        contextSupplier.put(name, supplier);
    }

    /**
     * Renders the template using the current supplied context values as UTF-8 content.
     * @return the successful step result
     * @throws FlowStepException if a context value is absent
     */
    @Override
    public FlowStepResult execute() {
        final Map<String, Object> context = new HashMap<>();

        contextSupplier.forEach((key, value) ->
            context.put(key, value.get().orElseThrow(() ->
                new FlowStepException("Value of context supplier '" + key + "' as not been set"))
            )
        );

        TesTemplate template = new TesTemplate(velocityEngine, templateName, () -> context);
        StringWriter writer = template.produce();

        fileContent.set(new DeployFileContent(filename, writer.toString().getBytes(StandardCharsets.UTF_8)));

        return FlowStepResult.OK;
    }

}
