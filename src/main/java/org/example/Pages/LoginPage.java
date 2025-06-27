package org.example.Pages;

import org.example.API.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.example.User;

public class LoginPage {
    WebDriver driver;
    //Заголовок "Вход"
    private final By headerLogin = By.xpath(".//h2[text()='Вход']");
    //Поле "Email"
    private final By inputEmail = By.xpath(".//label[text()='Email']/../input");
    //Поле "Пароль"
    private final By inputPassword = By.xpath(".//label[text()='Пароль']/../input");
    //Кнопка "Войти"
    private final By btnLogin = By.xpath(".//button[text()='Войти']");
    //Кнопка "Восстановить пароль"
    private final By textResetPassword = By.xpath(".//a[text()='Восстановить пароль']");
    //Кнопка "Личный кабинет"
    private final By btnProfile = By.xpath(".//p[text()='Личный Кабинет']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
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
        clickProfileButton();
    }
}
