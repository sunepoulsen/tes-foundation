package dk.sunepoulsen.tes.deployment.core.data;

import lombok.Builder;
import lombok.Data;

/**
 * Defines a user to be used with a System Under Test.
 */
@Data
@Builder
public class DeployUser {
    /** Username for the system under test. */
    private String username;
    /** Password for the user. */
    private String password;
}
