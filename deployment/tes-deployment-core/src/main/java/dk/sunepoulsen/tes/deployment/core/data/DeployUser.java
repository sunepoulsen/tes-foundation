package dk.sunepoulsen.tes.deployment.core.data;

import lombok.Builder;
import lombok.Data;

/**
 * Defines a user to be used with a System Under Test.
 */
@Data
@Builder
public class DeployUser {
    private String username;
    private String password;
}
