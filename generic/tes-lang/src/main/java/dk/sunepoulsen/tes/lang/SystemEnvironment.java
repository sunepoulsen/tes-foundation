package dk.sunepoulsen.tes.lang;

import java.util.Map;

/** Utilities for working with environment variables. */
public class SystemEnvironment {

    private SystemEnvironment() {
    }

    /**
     * Sets JAVA_HOME in a supplied environment map to the current Java home.
     *
     * @param environment map to update
     */
    public static void putJavaHome(final Map<String, String> environment) {
        environment.put("JAVA_HOME", System.getProperty("java.home"));
    }

    /**
     * Reads a nonblank environment variable.
     *
     * @param environmentVariableName name of the variable
     * @return the variable's value
     * @throws IllegalStateException if the variable is missing or blank
     */
    public static String readVariable(final String environmentVariableName) {
        String fromEnv = System.getenv(environmentVariableName);
        if (fromEnv != null && !fromEnv.isBlank()) {
            return fromEnv;
        }

        throw new IllegalStateException("Missing " + environmentVariableName);
    }

}
