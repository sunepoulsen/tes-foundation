package dk.sunepoulsen.tes.utils;

import dk.sunepoulsen.tes.utils.exceptions.PropertyResourceException;
import dk.sunepoulsen.tes.utils.exceptions.ResourceException;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Represents a resource of propertes (property files) and provide utility functions.
 */
@Getter
@Setter
public class PropertyResource {

    private static final String PROPERTY_KEY_NOT_FOUND_MESSAGE = "Unable to read property because the key '%s' does not exist";

    private Properties properties;

    public PropertyResource(InputStream inputStream) throws ResourceException {
        try {
            this.loadProperties(inputStream);
        } catch (IOException ex) {
            throw new ResourceException("Unable to read property resource from InputStream", ex);
        }
    }

    public boolean containsKey(String key) {
        return this.properties.containsKey(key);
    }

    public String property(String key) throws PropertyResourceException {
        if (!this.containsKey(key)) {
            throw new PropertyResourceException(String.format(PROPERTY_KEY_NOT_FOUND_MESSAGE, key));
        }

        return properties.getProperty(key);
    }

    public String property(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static <T> String readProperty(Class<T> clazz, String classpathResource, String property) throws ResourceException, PropertyResourceException {
        PropertyResource propertyResource = new PropertyResource(Resources.readResource(clazz, classpathResource));
        return propertyResource.property(property);
    }

    public static <T> String readProperty(Class<T> clazz, String classpathResource, String property, String defaultValue) throws ResourceException {
        PropertyResource propertyResource = new PropertyResource(Resources.readResource(clazz, classpathResource));
        return propertyResource.property(property, defaultValue);
    }

    public static String readProperty(String classpathResource, String property) throws ResourceException, PropertyResourceException {
        return readProperty(PropertyResource.class, classpathResource, property);
    }

    public static String readProperty(String classpathResource, String property, String defaultValue) throws ResourceException {
        return readProperty(PropertyResource.class, classpathResource, property, defaultValue);
    }

    private void loadProperties(InputStream inputStream) throws IOException {
        this.properties = new Properties();
        this.properties.load(inputStream);
    }

}
