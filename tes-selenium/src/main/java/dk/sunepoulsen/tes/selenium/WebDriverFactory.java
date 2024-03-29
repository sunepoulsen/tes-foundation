package dk.sunepoulsen.tes.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactory {
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
