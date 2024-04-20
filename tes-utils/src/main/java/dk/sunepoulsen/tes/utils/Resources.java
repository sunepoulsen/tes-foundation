package dk.sunepoulsen.tes.utils;

import dk.sunepoulsen.tes.utils.exceptions.ResourceException;

import java.io.InputStream;

/**
 * Load resources from the classpath.
 */
public class Resources {

    /**
     * Hide the constructor.
     */
    private Resources() {}

    /**
     * Read a resource from the classpath.
     *
     * @param <T> Class type to reead the resource with.
     * @param clazz {@code Class<T>} used to find and read the resource.
     * @param classpathResource Resource on the classpath.
     *
     * @return The loaded resource if its found.
     *
     * @exception ResourceException Thrown if the resource cant be read from the classpath.
     */
    public static <T> InputStream readResource(Class<T> clazz, String classpathResource) throws ResourceException {
        InputStream resourceStream = clazz.getResourceAsStream(classpathResource);
        if (resourceStream == null) {
            throw new ResourceException("Unable to load classpath resource '" + classpathResource + "'");
        }

        return resourceStream;
    }

    /**
     * Read a resource from the classpath.
     *
     * <p>
     * This {@code Resources} to load the resource from the classpath.
     *
     * @param classpathResource Resource on the classpath.
     *
     * @return The loaded resource if its found.
     *
     * @exception ResourceException Thrown if the resource cant be read from the classpath.
     */
    public static InputStream readResource(String classpathResource) throws ResourceException {
        return readResource(Resources.class, classpathResource);
    }

}
