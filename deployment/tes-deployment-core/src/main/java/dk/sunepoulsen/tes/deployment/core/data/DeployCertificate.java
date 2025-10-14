package dk.sunepoulsen.tes.deployment.core.data;

import lombok.*;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeployCertificate extends DeployFileContent {
    private final String password;

    public DeployCertificate(String filename, byte[] content, String password) {
        super(filename, content);
        this.password = password;
    }

}
