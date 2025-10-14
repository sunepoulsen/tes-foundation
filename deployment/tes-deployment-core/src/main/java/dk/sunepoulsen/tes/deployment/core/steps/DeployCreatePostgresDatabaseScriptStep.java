package dk.sunepoulsen.tes.deployment.core.steps;

import dk.sunepoulsen.tes.data.generators.FixedDataGenerator;
import dk.sunepoulsen.tes.deployment.core.data.DeployFileContent;
import dk.sunepoulsen.tes.deployment.core.data.DeployUser;
import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier;
import dk.sunepoulsen.tes.flows.FlowStepResult;
import dk.sunepoulsen.tes.flows.exceptions.FlowStepException;
import dk.sunepoulsen.tes.templates.TesTemplate;
import lombok.Getter;
import lombok.Setter;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeployCreatePostgresDatabaseScriptStep extends AbstractDeployStep {

    @Getter
    private final AtomicDataSupplier<DeployFileContent> createdScript;

    private final VelocityEngine velocityEngine;

    @Getter
    @Setter
    private String filename;

    @Getter
    @Setter
    private FixedDataGenerator<String> masterUsername;

    @Getter
    @Setter
    private FixedDataGenerator<String> databaseName;

    @Getter
    @Setter
    private FixedDataGenerator<String> databaseEncoding;

    @Getter
    @Setter
    private FixedDataGenerator<List<String>> schemaNames;

    @Getter
    @Setter
    private AtomicDataSupplier<DeployUser> adminUser;

    @Getter
    @Setter
    private AtomicDataSupplier<DeployUser> applicationUser;

    public DeployCreatePostgresDatabaseScriptStep(String key, VelocityEngine velocityEngine) {
        super(key);
        this.velocityEngine = velocityEngine;
        this.createdScript = new AtomicDataSupplier<>();
    }

    @Override
    public FlowStepResult execute() {
        TesTemplate template = new TesTemplate(velocityEngine, "templates/postgresql-create-database.sql", this::contextProvider);
        StringWriter writer = template.produce();

        this.createdScript.set(new DeployFileContent(filename, writer.toString().getBytes(StandardCharsets.UTF_8)));

        return FlowStepResult.OK;
    }

    private Map<String, Object> contextProvider() {
        DeployUser sutAdminUser = adminUser.get().orElseThrow(() ->
            new FlowStepException("Admin user has not been set")
        );
        DeployUser sutApplicationUser = applicationUser.get().orElseThrow(() ->
            new FlowStepException("Application user has not been set")
        );

        Map<String, Object> context = new HashMap<>();
        context.put("masterUsername", masterUsername.generate());
        context.put("databaseName", databaseName.generate());
        context.put("databaseEncoding", databaseEncoding.generate());
        context.put("adminUser", DeployUser.builder()
            .username(sutAdminUser.getUsername())
            .password(sutAdminUser.getPassword().replace("'", "''"))
            .build()
        );
        context.put("applicationUser", DeployUser.builder()
            .username(sutApplicationUser.getUsername())
            .password(sutApplicationUser.getPassword().replace("'", "''"))
            .build()
        );
        context.put("schemas", schemaNames.generate());

        return context;
    }

}
