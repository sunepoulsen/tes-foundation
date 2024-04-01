package dk.sunepoulsen.tes.docker.containers;

import dk.sunepoulsen.tes.docker.exceptions.DockerImageProviderException;
import lombok.Data;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
            Properties props = new Properties();
            props.load(loadResource(classpathResource));

            String imageName = readProperty(props, propertyKeyPrefix + IMAGE_KEY_NAME_SUFFIX);
            String imageTag = readProperty(props, propertyKeyPrefix + TAG_KEY_NAME_SUFFIX);

            return DockerImageName.parse(imageName)
                .withTag(imageTag);
        } catch (IOException ex) {
            throw new DockerImageProviderException("Unable to docker image name from classpath resource " + classpathResource, ex);
        }
    }

    private InputStream loadResource(String classpathResource) throws DockerImageProviderException {
        InputStream resourceStream = getClass().getResourceAsStream(classpathResource);
        if (resourceStream == null) {
            throw new DockerImageProviderException("Unable to load classpath resource '" + classpathResource + "'");
        }

        return resourceStream;
    }

    private String readProperty(Properties props, String key) throws DockerImageProviderException {
        if (!props.containsKey(key)) {
            throw new DockerImageProviderException("Unable to read key '" + key + "' from resource");
        }

        return props.getProperty(key);
    }
}
