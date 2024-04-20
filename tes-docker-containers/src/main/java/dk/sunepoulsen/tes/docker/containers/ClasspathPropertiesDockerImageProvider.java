package dk.sunepoulsen.tes.docker.containers;

import dk.sunepoulsen.tes.docker.exceptions.DockerImageProviderException;
import dk.sunepoulsen.tes.utils.PropertyResource;
import dk.sunepoulsen.tes.utils.Resources;
import dk.sunepoulsen.tes.utils.exceptions.PropertyResourceException;
import dk.sunepoulsen.tes.utils.exceptions.ResourceException;
import lombok.Data;
import org.testcontainers.utility.DockerImageName;

/**
 * Docker image provider that reads the docker image name from a properties
 * file on the classpath.
 * <p>
 * The name of classpath resource must be a complete path.
 */
@Data
public class ClasspathPropertiesDockerImageProvider implements DockerImageProvider {
    private static final String IMAGE_KEY_NAME_SUFFIX = ".image.name";
    private static final String TAG_KEY_NAME_SUFFIX = ".image.tag";

    private String classpathResource;
    private String propertyKeyPrefix;

    public ClasspathPropertiesDockerImageProvider(String classpathResource, String propertyKeyPrefix) {
        this.classpathResource = classpathResource;
        this.propertyKeyPrefix = propertyKeyPrefix;
    }

    @Override
    public DockerImageName dockerImageName() throws DockerImageProviderException {
        try {
            PropertyResource resource = new PropertyResource(Resources.readResource(classpathResource));

            String imageName = resource.property(propertyKeyPrefix + IMAGE_KEY_NAME_SUFFIX);
            String imageTag = resource.property(propertyKeyPrefix + TAG_KEY_NAME_SUFFIX);

            return DockerImageName.parse(imageName)
                .withTag(imageTag);
        } catch (ResourceException | PropertyResourceException ex) {
            throw new DockerImageProviderException("Unable to docker image name from classpath resource " + classpathResource, ex);
        }
    }
}
