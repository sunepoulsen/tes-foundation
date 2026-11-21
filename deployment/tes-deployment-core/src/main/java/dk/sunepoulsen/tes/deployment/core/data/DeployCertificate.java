package dk.sunepoulsen.tes.deployment.core.data;

import lombok.*;

/** A certificate file and its password. */
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeployCertificate extends DeployFileContent {
    /** Password protecting the certificate. */
    private final String password;

    /**
     * Creates certificate file content.
     * @param filename certificate filename
     * @param content certificate bytes
     * @param password certificate password
     */
    public DeployCertificate(String filename, byte[] content, String password) {
        super(filename, content);
        this.password = password;
    }

}
