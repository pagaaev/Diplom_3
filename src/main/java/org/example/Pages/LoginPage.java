package org.example.Pages;

import org.example.API.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage package org.example.pages;

import io.qameta.allure.Step;
import org.example.api.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    WebDriver driver;

    // Заголовок "Вход"
    private final By headerLogin = By.xpath(".//h2[text()='Вход']");
    // Поле "Email"
    private final By inputEmail = By.xpath(".//label[text()='Email']/../input");
    // Поле "Пароль"
    private final By inputPassword = By.xpath(".//label[text()='Пароль']/../input");
    // Кнопка "Войти"
    private final By btnLogin = By.xpath(".//button[text()='Войти']");
    // Кнопка "Восстановить пароль"
    private final By textResetPassword = By.xpath(".//a[text()='Восстановить пароль']");
    // Кнопка "Личный кабинет"
    private final By btnProfile = By.xpath(".//p[text()='Личный Кабинет']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Получить текст заголовка страницы логина")
    public String getLoginTextFromHeader() {
        return driver.findElement(headerLogin).getText();
    }

    @Step("Вводим email: {email}")
    public void setEmail(String email) {
        driver.findElement(inputEmail).sendKeys(email);
    }

    @Step("Вводим пароль")
    public void setPassword(String password) {
        driver.findElement(inputPassword).sendKeys(password);
    }

    @Step("Нажимаем кнопку 'Войти'")
    public void clickLoginButton() {
        driver.findElement(btnLogin).click();
    }

    @Step("Нажимаем кнопку 'Личный кабинет'")
    public void clickProfileButton() {
        driver.findElement(btnProfile).click();
    }

    @Step("Логинимся пользователем с email: {user.email}")
    public void loginUser(User user) {
        setEmail(user.getEmail());
        setPassword(user.getPassword());
        clickLoginButton();
        clickProfileButton();
    }
}
