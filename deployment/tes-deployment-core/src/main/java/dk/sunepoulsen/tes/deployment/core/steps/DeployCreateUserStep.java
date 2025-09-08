package dk.sunepoulsen.tes.deployment.core.steps;

import dk.sunepoulsen.tes.data.generators.DataGenerator;
import dk.sunepoulsen.tes.deployment.core.data.DeployUser;
import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier;
import dk.sunepoulsen.tes.flows.FlowStepResult;
import lombok.Getter;

public class DeployCreateUserStep extends AbstractDeployStep {

    @Getter
    private final AtomicDataSupplier<DeployUser> createdUser;

    private final DataGenerator<String> username;
    private final DataGenerator<String> password;

    public DeployCreateUserStep(final String key, final DataGenerator<String> username, final DataGenerator<String> password) {
        super(key);
        this.createdUser = new AtomicDataSupplier<>();
        this.username = username;
        this.password = password;
    }

    @Override
    public FlowStepResult execute() {
        if (username == null) {
            throw new IllegalStateException("username may not be null");
        } else if (password == null) {
            throw new IllegalStateException("password may not be null");
        }

        this.createdUser.set(DeployUser.builder()
            .username(username.generate())
            .password(password.generate())
            .build());

        return FlowStepResult.OK;
    }

}
