package dk.sunepoulsen.tes.templates;

import lombok.RequiredArgsConstructor;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.Map;
import java.util.function.Supplier;

/** Renders a named Velocity template using a supplied context. */
@RequiredArgsConstructor
public class TesTemplate {

    /** Engine used to load the template. */
    private final VelocityEngine velocityEngine;
    /** Name of the template to load. */
    private final String templateName;
    /** Supplies the values used for each rendering. */
    private final Supplier<Map<String, Object>> contextSupplier;

    /**
     * Loads and renders the template using a fresh context.
     *
     * @return a writer containing the rendered template
     */
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
