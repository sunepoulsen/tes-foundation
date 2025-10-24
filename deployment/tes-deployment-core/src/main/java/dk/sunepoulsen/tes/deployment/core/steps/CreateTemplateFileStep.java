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

public class CreateTemplateFileStep extends AbstractDeployStep {

    @Getter
    @Setter
    private AtomicDataSupplier<DeployFileContent> fileContent;

    @Getter
    @Setter
    private String filename;

    private final VelocityEngine velocityEngine;
    private final String templateName;
    private final Map<String, AtomicDataSupplier<?>> contextSupplier;

    public CreateTemplateFileStep(String key, VelocityEngine velocityEngine, String templateName) {
        super(key);
        this.fileContent = new AtomicDataSupplier<>();
        this.velocityEngine = velocityEngine;
        this.templateName = templateName;
        this.contextSupplier = new HashMap<>();
    }

    public void addContextSupplier(String name, AtomicDataSupplier<?> supplier) {
        contextSupplier.put(name, supplier);
    }

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
