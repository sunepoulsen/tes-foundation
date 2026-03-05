package dk.sunepoulsen.tes.selenium.verifiers;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URI;
import java.time.Duration;

public class OpenApiVerifier extends GeneralVerifier {
    private final WebVerifier webVerifier;

    public OpenApiVerifier(WebDriver webDriver, URI uri) {
        this(webDriver, uri, Duration.ofMinutes(1));
    }

    public OpenApiVerifier(WebDriver webDriver, URI uri, Duration waitElementDuration) {
        super(webDriver, uri, waitElementDuration);
        this.webVerifier = new WebVerifier(webDriver, uri, waitElementDuration);
    }

    public void verifyTitle(String title) {
        webVerifier.verifyVisibleTextElement(By.cssSelector("h2.title"), title);
    }

    public void verifyEndpoint(String id, String method, String path) {
        final WebDriverWait wait = new WebDriverWait(webDriver, this.waitElementDuration);

        final WebElement operationsElement = wait
            .until(ExpectedConditions.presenceOfElementLocated(By.id("operations-" + id)));
        Assertions.assertNotNull(operationsElement);

        final WebElement methodElement = wait
            .until(webDriver1 -> operationsElement.findElement(By.cssSelector("span.opblock-summary-method")));
        Assertions.assertNotNull(methodElement);
        Assertions.assertEquals(method.toUpperCase(), methodElement.getText().toUpperCase());

        final WebElement pathElement = wait
            .until(webDriver1 -> operationsElement.findElement(By.cssSelector("span.opblock-summary-path")));
        Assertions.assertNotNull(pathElement);

        final String dataPath = pathElement.getAttribute("data-path");
        Assertions.assertNotNull(dataPath, "The attribute 'data-path' does not exist on element " + pathElement.getTagName());
        Assertions.assertEquals(path.toLowerCase(), dataPath.toLowerCase());
    }
}
