package dk.sunepoulsen.tes.templates;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/// Factory class to create instances of `VelocityEngine`
///
/// New instances will be setup with these properties:
/// - Template files will be loaded from the classpath.
///
/// @since 2.1.0
/// @author sunepoulsen
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VelocityEngineFactory {

    /// Constructs a new instance of `VelocityEngine`
    ///
    /// @return The new instance of `VelocityEngine`
    ///
    /// @since 2.1.0
    /// @author sunepoulsen
    public static VelocityEngine newInstance() {
        VelocityEngine instance = new VelocityEngine();
        instance.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        instance.setProperty("classpath.resource.loader.class",
            ClasspathResourceLoader.class.getName());
        instance.init();

        return instance;
    }

}
