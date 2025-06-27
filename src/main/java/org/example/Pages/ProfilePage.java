package org.example.Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProfilePage {
    private final WebDriver driver;

    // Локаторы элементов
    private final By btnPersonalCabinet = By.xpath("//p[contains(text(),'Личный Кабинет')]");
    private final By btnProfileTab = By.xpath("//a[text()='Профиль']");
    private final By btnExitTab = By.xpath("//button[text()='Выход']");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Получить локатор кнопки 'Профиль'")
    public By getBtnProfileTabLocator() {
        return btnProfileTab;
    }

    @Step("Проверить, что вкладка 'Профиль' активна")
    public boolean btnProfileTabIsEnabled() {
        return driver.findElement(btnProfileTab).isEnabled();
    }

    @Step("Нажать кнопку 'Выход'")
    public void clickExitButton() {
        driver.findElement(btnExitTab).click();
    }

    @Step("Открыть 'Личный Кабинет' и дождаться доступности кнопки 'Профиль'")
    public void openPersonalCabinetAndWaitForProfileTab() {
        // Кликаем на "Личный Кабинет"
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(btnPersonalCabinet))
                .click();

        // Ждём появления вкладки "Профиль"
        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.elementToBeClickable(btnProfileTab));
    }

}
