package dk.sunepoulsen.tes.deployment.core.steps;

import dk.sunepoulsen.tes.data.generators.FixedDataGenerator;
import dk.sunepoulsen.tes.deployment.core.data.DeployUser;
import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier;
import dk.sunepoulsen.tes.flows.FlowStepResult;
import lombok.Getter;

@Getter
public class DeployCreateUserStep extends AbstractDeployStep {

    private final AtomicDataSupplier<DeployUser> createdUser;
    private final FixedDataGenerator<String> username;
    private final FixedDataGenerator<String> password;

    public DeployCreateUserStep(final String key, final FixedDataGenerator<String> username, final FixedDataGenerator<String> password) {
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
