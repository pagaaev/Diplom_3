import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.example.api.*;
import org.example.Pages.LoginPage;
import org.example.Pages.MainPage;
import org.example.Pages.ProfilePage;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;

public class LogoutTest {
    private WebDriver driver;
    private User user;

    @Before
    public void setup() {
        RestAssured.baseURI = BaseURL.BASE_URI;
        user = Generator.generateUser();
        UserOperations.createUser(user);

        driver = WebDriverConfig.setDriver();
        driver.manage().timeouts().implicitlyWait(WebDriverConfig.WAIT_SEC_TIMEOUT, TimeUnit.SECONDS);
        driver.navigate().to(APIconfig.MAIN_PAGE_URL);
    }

    @Test
    @DisplayName("Log out and return to the login page")
    public void logoutGetSuccess() {
        LoginPage loginPage = new LoginPage(driver);
        MainPage mainPage = new MainPage(driver);
        ProfilePage profilePage = new ProfilePage(driver);

        loginUser(loginPage, user);        // 1. логин
        goToProfile(mainPage);             // 2. переход в личный кабинет
        clickLogout(profilePage);          // 3. выход

        waitUntilLoginVisible(loginPage);  // 4. проверка возврата
        MatcherAssert.assertThat(loginPage.getLoginTextFromHeader(), equalTo("Вход"));
    }

    @After
    public void teardown() {
        String token = UserOperations.getAccessToken(user);
        if (token != null) {
            UserOperations.deleteUser(token);
        }
        if (driver != null) {
            driver.quit();
        }
    }

    @Step("Вход пользователя: {user.email}")
    private void loginUser(LoginPage loginPage, User user) {
        loginPage.clickProfileButton();  // сначала жмем "Личный Кабинет" для показа формы логина
        loginPage.loginUser(user);
    }

    @Step("Переход в личный кабинет с главной страницы")
    private void goToProfile(MainPage mainPage) {
        mainPage.clickProfileButton();
    }

    @Step("Клик по кнопке 'Выход'")
    private void clickLogout(ProfilePage profilePage) {
        profilePage.clickExitButton();
    }

    @Step("Ожидание появления заголовка 'Вход'")
    private void waitUntilLoginVisible(LoginPage loginPage) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.getHeaderLoginLocator()));
    }
}
