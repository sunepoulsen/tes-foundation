package dk.sunepoulsen.tes.selenium.verifiers;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

/** Common navigation and URL assertions for browser tests. */
public class GeneralVerifier {
    protected Duration waitElementDuration;

    protected final WebDriver webDriver;
    /** Base URI used to resolve navigation targets. */
    private final URI uri;

    /**
     * Creates a verifier with a one-minute element wait.
     * @param webDriver browser driver
     * @param uri base URI for navigation
     */
    public GeneralVerifier(final WebDriver webDriver, final URI uri) {
        this(webDriver, uri, Duration.ofMinutes(1));
    }

    /**
     * Creates a verifier with a configurable element wait.
     * @param webDriver browser driver
     * @param uri base URI for navigation
     * @param waitElementDuration element wait duration
     */
    public GeneralVerifier(final WebDriver webDriver, final URI uri, final Duration waitElementDuration) {
        this.webDriver = webDriver;
        this.uri = uri;
        this.waitElementDuration = waitElementDuration;
    }

    /**
     * Navigates to a URI resolved against the base URI.
     * @param relativeUri URI reference to visit
     */
    public void visit(String relativeUri) {
        webDriver.get(this.uri.resolve(relativeUri).toString());
    }

    /**
     * Asserts the path of the browser's current URL.
     * @param expected expected path
     * @throws URISyntaxException if the current URL cannot be parsed
     * @throws AssertionError if the path differs
     */
    public void verifyCurrentUrlPath(String expected) throws URISyntaxException {
        URI currentUri = new URI(webDriver.getCurrentUrl());
        Assertions.assertEquals(expected, currentUri.getPath());
    }

}
