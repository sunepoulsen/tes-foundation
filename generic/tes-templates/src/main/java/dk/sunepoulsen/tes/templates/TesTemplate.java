package dk.sunepoulsen.tes.templates;

import lombok.RequiredArgsConstructor;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.Map;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class TesTemplate {

    private final VelocityEngine velocityEngine;
    private final String templateName;
    private final Supplier<Map<String, Object>> contextSupplier;

    public StringWriter produce() {
        Template template = velocityEngine.getTemplate(templateName);

        VelocityContext context = new VelocityContext();
        for (Map.Entry<String, Object> entry : contextSupplier.get().entrySet()) {
            context.put(entry.getKey(), entry.getValue());
        }

        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        return writer;
    }
}
