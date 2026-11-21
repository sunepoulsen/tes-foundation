package dk.sunepoulsen.tes.io.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/** Loads properties from an input stream and provides access to their values. */
public class PropertiesResource {

    /** Properties loaded from the resource. */
    private final Properties properties;

    /**
     * Loads properties from the supplied stream without closing it.
     *
     * @param inputStream stream containing properties
     * @throws ResourceException if the stream is null or cannot be read
     */
    public PropertiesResource(InputStream inputStream) throws ResourceException {
        this.properties = new Properties();
        loadProperties(inputStream);
    }

    /**
     * Returns the property with the given name.
     *
     * @param name property name
     * @return the property value, or null if absent
     */
    public String getProperty(String name) {
        return properties.getProperty(name);
    }

    private void loadProperties(InputStream inputStream) throws ResourceException {
        try {
            properties.load(Objects.requireNonNull(inputStream));
        } catch (IOException ex) {
            throw new ResourceException("Unable to load properties from resource", ex);
        } catch (NullPointerException ex) {
            throw new ResourceException("Unable to load properties from null resource", ex);
        }
    }

}
