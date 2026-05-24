package dk.sunepoulsen.tes.gatling.checks;

import dk.sunepoulsen.tes.gatling.exceptions.GatlingCheckException;
import io.gatling.javaapi.core.Session;

import java.util.function.Function;

public class GatlingChecks {

    private GatlingChecks() {
    }

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
