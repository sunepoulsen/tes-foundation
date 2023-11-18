package dk.sunepoulsen.tes.selenium.verifiers;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class GeneralVerifier {
    protected Duration waitElementDuration;

    protected final WebDriver webDriver;
    private final URI uri;

    public GeneralVerifier(final WebDriver webDriver, final URI uri) {
        this(webDriver, uri, Duration.ofMinutes(1));
    }

    public GeneralVerifier(final WebDriver webDriver, final URI uri, final Duration waitElementDuration) {
        this.webDriver = webDriver;
        this.uri = uri;
        this.waitElementDuration = waitElementDuration;
    }

    public void visit(String relativeUri) {
        webDriver.get(this.uri.resolve(relativeUri).toString());
    }

    public void verifyCurrentUrlPath(String expected) throws URISyntaxException {
        URI uri = new URI(webDriver.getCurrentUrl());
        Assertions.assertEquals(expected, uri.getPath());
    }

}
