package dk.sunepoulsen.tes.selenium.verifiers;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URI;
import java.time.Duration;

/** Assertions for elements on a web page. */
public class WebVerifier extends GeneralVerifier {

    /**
     * Creates a verifier with a one-minute element wait.
     * @param webDriver browser driver
     * @param uri base URI for navigation
     */
    public WebVerifier(WebDriver webDriver, URI uri) {
        this(webDriver, uri, Duration.ofMinutes(1));
    }

    /**
     * Creates a verifier with a configurable element wait.
     * @param webDriver browser driver
     * @param uri base URI for navigation
     * @param waitElementDuration element wait duration
     */
    public WebVerifier(WebDriver webDriver, URI uri, Duration waitElementDuration) {
        super(webDriver, uri, waitElementDuration);
    }

    /**
     * Checks an element's visibility and text using the configured wait.
     * @param by element locator
     * @param expectedText expected text
     * @throws AssertionError if the element is not visible or its text differs
     */
    public void verifyVisibleTextElement(By by, String expectedText) {
        verifyVisibleTextElement(by, this.waitElementDuration, expectedText);
    }

    /**
     * Waits for an element to be present, then checks its visibility and text.
     * @param by element locator
     * @param duration maximum presence wait
     * @param expectedText expected text
     * @throws AssertionError if the element is not visible or its text differs
     */
    public void verifyVisibleTextElement(By by, Duration duration, String expectedText) {
        new WebDriverWait(webDriver, duration)
            .until(ExpectedConditions.presenceOfElementLocated(by));
        assertVisibleTextElement(webDriver.findElement(by), expectedText);
    }

    private void assertVisibleTextElement(WebElement element, String expectedText) {
        Assertions.assertNotNull(element, "No element was found");
        Assertions.assertTrue(element.isDisplayed(), String.format("The element '%s' is not visible", element.getTagName()));
        Assertions.assertEquals(expectedText, element.getText());
    }
}
