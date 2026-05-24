package dk.sunepoulsen.tes.lang;

import java.util.Map;

public class SystemEnvironment {

    private SystemEnvironment() {
    }

    public static void putJavaHome(final Map<String, String> environment) {
        environment.put("JAVA_HOME", System.getProperty("java.home"));
    }

    public static String readVariable(final String environmentVariableName) {
        String fromEnv = System.getenv(environmentVariableName);
        if (fromEnv != null && !fromEnv.isBlank()) {
            return fromEnv;
        }

        throw new IllegalStateException("Missing " + environmentVariableName);
    }

}
