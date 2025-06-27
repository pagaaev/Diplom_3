package org.example.api;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverConfig {
    public static final long WAIT_SEC_TIMEOUT = 10;

    public static WebDriver setDriver() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();

        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();

            case "yandex":
                WebDriverManager.chromedriver().setup(); // можно использовать тот же chromedriver
                ChromeOptions options = new ChromeOptions();
                options.setBinary("/Applications/Yandex.app/Contents/MacOS/Yandex");
                return new ChromeDriver(options);

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }
}
