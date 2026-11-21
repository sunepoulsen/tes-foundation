package dk.sunepoulsen.tes.gatling.checks;

import dk.sunepoulsen.tes.gatling.exceptions.GatlingCheckException;
import io.gatling.javaapi.core.Session;

import java.util.function.Function;

/** Utilities for checking Gatling session properties. */
public class GatlingChecks {

    private GatlingChecks() {
    }

    /**
     * Creates a check comparing two session properties with {@link Object#equals(Object)}.
     *
     * @param checkDescription description included in a failure message
     * @param actualProperty name of the actual property
     * @param expectedProperty name of the expected property
     * @return a function returning the original session, or throwing {@link GatlingCheckException} when values differ
     */
    public static Function<Session, Session> sessionPropertyEquals(final String checkDescription, final String actualProperty, final String expectedProperty) {
        return session -> {
            Object actual = session.get(actualProperty);
            Object expected = session.get(expectedProperty);

            if (!actual.equals(expected)) {
                throw new GatlingCheckException(
                    "%s: '%s' != '%s'".formatted(checkDescription, actual, expected)
                );
            }
            return session;
        };
    }

}
