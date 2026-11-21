package dk.sunepoulsen.tes.deployment.core.steps;

import dk.sunepoulsen.tes.data.generators.FixedDataGenerator;
import dk.sunepoulsen.tes.deployment.core.data.DeployUser;
import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier;
import dk.sunepoulsen.tes.flows.FlowStepResult;
import lombok.Getter;

/** Generates credentials and exposes a deployment user. */
@Getter
public class DeployCreateUserStep extends AbstractDeployStep {

    /** Receives the generated user. */
    private final AtomicDataSupplier<DeployUser> createdUser;
    /** Generates the username. */
    private final FixedDataGenerator<String> username;
    /** Generates the password. */
    private final FixedDataGenerator<String> password;

    /**
     * Creates a user generation step.
     * @param key step and timer key
     * @param username username generator
     * @param password password generator
     */
    public DeployCreateUserStep(final String key, final FixedDataGenerator<String> username, final FixedDataGenerator<String> password) {
        super(key);
        this.createdUser = new AtomicDataSupplier<>();
        this.username = username;
        this.password = password;
    }

    /**
     * Generates and stores a user.
     * @return the successful step result
     * @throws IllegalStateException if either generator is null
     */
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
