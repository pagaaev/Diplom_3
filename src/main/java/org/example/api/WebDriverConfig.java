package org.example.api;

import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverConfig {
    public static final long WAIT_SEC_TIMEOUT = 10;

    public static WebDriver setDriver() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();

        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                // Можно добавить настройки firefoxOptions
                return new FirefoxDriver(firefoxOptions);

            case "chrome":
            default:
                WebDriverManager.chromedriver().driverVersion("138.0.7204.50").setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                // Можно добавить настройки chromeOptions
                return new ChromeDriver(chromeOptions);
        }
    }
}
