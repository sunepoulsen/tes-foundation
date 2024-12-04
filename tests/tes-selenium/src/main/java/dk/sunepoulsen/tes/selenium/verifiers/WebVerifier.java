package dk.sunepoulsen.tes.selenium.verifiers;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URI;
import java.time.Duration;

public class WebVerifier extends GeneralVerifier {

    public WebVerifier(WebDriver webDriver, URI uri) {
        this(webDriver, uri, Duration.ofMinutes(1));
    }

    public WebVerifier(WebDriver webDriver, URI uri, Duration waitElementDuration) {
        super(webDriver, uri, waitElementDuration);
    }

    public void verifyVisibleTextElement(By by, String expectedText) {
        verifyVisibleTextElement(by, this.waitElementDuration, expectedText);
    }

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
