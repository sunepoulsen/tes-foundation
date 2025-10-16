package dk.sunepoulsen.tes.io.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class PropertiesResource {

    private final Properties properties;

    public PropertiesResource(InputStream inputStream) throws ResourceException {
        this.properties = new Properties();
        loadProperties(inputStream);
    }

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
