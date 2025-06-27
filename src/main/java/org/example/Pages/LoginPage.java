package org.example.Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.example.api.User;

public class LoginPage {
    private final WebDriver driver;

    // Локаторы
    private final By headerLogin = By.xpath(".//h2[text()='Вход']");
    private final By inputEmail = By.xpath(".//label[text()='Email']/../input");
    private final By inputPassword = By.xpath(".//label[text()='Пароль']/../input");
    private final By btnLogin = By.xpath(".//button[text()='Войти']");
    private final By textResetPassword = By.xpath(".//a[text()='Восстановить пароль']");
    private final By btnProfile = By.xpath(".//p[text()='Личный Кабинет']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Новый метод для получения локатора заголовка
    public By getHeaderLoginLocator() {
        return headerLogin;
    }

    @Step("Получить текст заголовка 'Вход'")
    public String getLoginTextFromHeader() {
        return driver.findElement(headerLogin).getText();
    }

    @Step("Ввести email: {email}")
    public void setEmail(String email) {
        driver.findElement(inputEmail).sendKeys(email);
    }

    @Step("Ввести пароль")
    public void setPassword(String password) {
        driver.findElement(inputPassword).sendKeys(password);
    }

    @Step("Нажать кнопку 'Войти'")
    public void clickLoginButton() {
        driver.findElement(btnLogin).click();
    }

    @Step("Нажать кнопку 'Личный кабинет'")
    public void clickProfileButton() {
        driver.findElement(btnProfile).click();
    }

    @Step("Выполнить логин пользователя с email: {user.email}")
    public void loginUser(User user) {
        setEmail(user.getEmail());
        setPassword(user.getPassword());
        clickLoginButton();
    }
}