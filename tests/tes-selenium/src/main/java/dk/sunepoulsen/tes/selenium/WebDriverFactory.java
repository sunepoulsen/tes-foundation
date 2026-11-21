package dk.sunepoulsen.tes.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/** Creates WebDriver instances for browser tests. */
public class WebDriverFactory {
    /**
     * Creates a headless Chrome driver that accepts insecure certificates.
     * @return the new WebDriver
     */
    public WebDriver createWebDriver() {
        return createChromeDriver();
    }

    private WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.setAcceptInsecureCerts(true);
        options.addArguments("--headless=new");

        return new ChromeDriver(options);
    }
}
