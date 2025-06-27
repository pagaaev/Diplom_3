import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.example.api.*;
import org.example.Pages.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class LoginTest {

    private WebDriver driver;
    User user;

    @Before
    public void setup() {
        RestAssured.baseURI = BaseURL.BASE_URI;
        user = Generator.generateUser();
        UserOperations.createUser(user);

        driver = WebDriverConfig.setDriver();
        driver.manage().timeouts().implicitlyWait(WebDriverConfig.WAIT_SEC_TIMEOUT, TimeUnit.SECONDS);
    }

    @Test
    @DisplayName("Login from the main page")
    public void loginOnMainPageGetSuccess() {
        driver.navigate().to(APIconfig.MAIN_PAGE_URL);
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        ProfilePage profilePage = new ProfilePage(driver);

        mainPage.clickLoginButton();
        loginUser(loginPage, user);
        waitForProfileTab(profilePage);

        Assert.assertTrue("Кнопка профиля недоступна", profilePage.btnProfileTabIsEnabled());
    }

    @Test
    @DisplayName("Login from the profile")
    public void loginWithProfileGetSuccess() {
        driver.navigate().to(APIconfig.MAIN_PAGE_URL);
        LoginPage loginPage = new LoginPage(driver);
        ProfilePage profilePage = new ProfilePage(driver);

        loginPage.clickProfileButton();
        loginUser(loginPage, user);
        waitForProfileTab(profilePage);

        Assert.assertTrue("Кнопка профиля недоступна", profilePage.btnProfileTabIsEnabled());
    }

    @Test
    @DisplayName("Login from the register page")
    public void loginWithRegisterPageGetSuccess() {
        driver.navigate().to(APIconfig.REGISTER_PAGE_URL);
        RegisterPage registerPage = new RegisterPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        ProfilePage profilePage = new ProfilePage(driver);

        registerPage.clickLoginButtonUnderReg();
        loginUser(loginPage, user);
        waitForProfileTab(profilePage);

        Assert.assertTrue("Кнопка профиля недоступна", profilePage.btnProfileTabIsEnabled());
    }

    @Test
    @DisplayName("Login from the reset password page")
    public void loginWithResetPasswordPageGetSuccess() {
        driver.navigate().to(APIconfig.RESET_PASSWORD_PAGE_URL);
        ResetPasswordPage resetPasswordPage = new ResetPasswordPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        ProfilePage profilePage = new ProfilePage(driver);

        resetPasswordPage.clickLoginButtonUnderResetting();
        loginUser(loginPage, user);
        waitForProfileTab(profilePage);

        Assert.assertTrue("Кнопка профиля недоступна", profilePage.btnProfileTabIsEnabled());
    }

    @After
    public void teardown() {
        UserOperations.deleteUser(UserOperations.getAccessToken(user));
        if (driver != null) {
            driver.quit();
        }
    }

    @Step("Логин пользователя: {user.email}")
    private void loginUser(LoginPage loginPage, User user) {
        loginPage.loginUser(user);
        System.out.println("Логин выполнен для пользователя: " + user.getEmail());

        // Дополнительная проверка (временное логирование)
        try {
            Thread.sleep(5000); // только для отладки, убрать потом
            System.out.println("URL после логина: " + driver.getCurrentUrl());
            System.out.println("Фрагмент страницы:\n" + driver.getPageSource().substring(0, 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Step("Ожидание загрузки вкладки Профиль")
    private void waitForProfileTab(ProfilePage profilePage) {
        System.out.println("Ожидаем появления вкладки 'Профиль'...");

        // Локатор кнопки "Личный Кабинет"
        By btnPersonalCabinet = By.xpath("//p[contains(text(),'Личный Кабинет')]");

        // Ждем и кликаем на "Личный Кабинет"
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(btnPersonalCabinet))
                .click();

        // Ждем появления кнопки "Профиль"
        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.elementToBeClickable(profilePage.getBtnProfileTabLocator()));
    }

}
