package org.example.Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilePage {
    WebDriver driver;

    // Вкладка "Профиль"
    private final By btnProfileTab = By.xpath(".//a[text()='Профиль']");
    // Вкладка "Выход"
    private final By btnExitTab = By.xpath(".//button[text()='Выход']");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Проверить, что вкладка 'Профиль' активна")
    public boolean btnProfileTabIsEnabled() {
        return driver.findElement(btnProfileTab).isEnabled();
    }

    @Step("Нажать кнопку 'Выход'")
    public void clickExitButton() {
        driver.findElement(btnExitTab).click();
    }
}
