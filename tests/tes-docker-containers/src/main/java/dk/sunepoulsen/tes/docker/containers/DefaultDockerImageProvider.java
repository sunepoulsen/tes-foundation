package dk.sunepoulsen.tes.docker.containers;

import dk.sunepoulsen.tes.docker.exceptions.DockerImageProviderException;
import lombok.Data;
import org.testcontainers.utility.DockerImageName;

@Data
public class DefaultDockerImageProvider implements DockerImageProvider {
    private String imageName;
    private String tagName;

    public DefaultDockerImageProvider(String imageName, String tagName) {
        this.imageName = imageName;
        this.tagName = tagName;
    }

    @Override
    public DockerImageName dockerImageName() throws DockerImageProviderException {
        return DockerImageName.parse(imageName)
            .withTag(tagName);
    }
}
