import io.qameta.allure.junit4.DisplayName;
import org.example.API.*;
import org.example.Pages.LoginPage;
import org.example.Pages.ProfilePage;
import org.example.Pages.RegisterPage;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;

public class RegisterTest package org.example.tests;

import io.qameta.allure.junit4.DisplayName;
import org.example.api.Generator;
import org.example.api.User;
import org.example.api.UserOperations;
import org.example.config.APIconfig;
import org.example.config.WebDriverConfig;
import org.example.pages.RegisterPage;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;

public class RegisterTest {
    private WebDriver driver;
    private User user;

    @Before
    public void setup() {
        user = Generator.generateUser();
        driver = WebDriverConfig.setDriver();
        driver.manage().timeouts().implicitlyWait(WebDriverConfig.WAIT_SEC_TIMEOUT, TimeUnit.SECONDS);
        driver.navigate().to(APIconfig.REGISTER_PAGE_URL);
    }

    @Test
    @DisplayName("Register new user with short password - negative test")
    public void registerNewUserWithShortPasswordGetError() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.setName(user.getName());
        registerPage.setEmail(user.getEmail());
        registerPage.setPassword(Generator.generateWrongUserPassword());
        registerPage.clickRegisterButton();
        MatcherAssert.assertThat(registerPage.getInvalidPasswordText(), equalTo("Некорректный пароль"));
    }

    @Test
    @DisplayName("Register new user successfully - positive test")
    public void registerNewUserSuccessfully() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.setName(user.getName());
        registerPage.setEmail(user.getEmail());
        registerPage.setPassword(user.getPassword());
        registerPage.clickRegisterButton();

        // Проверим, что после регистрации пользователь попадает на страницу логина (заголовок "Вход")
        MatcherAssert.assertThat(registerPage.getLoginHeaderText(), equalTo("Вход"));
    }

    @After
    public void teardown() {
        UserOperations.deleteUser(UserOperations.getAccessToken(user));
        driver.quit();
    }
}
