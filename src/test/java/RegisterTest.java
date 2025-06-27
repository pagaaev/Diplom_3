import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.example.api.*;
import org.example.Pages.RegisterPage;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;

public class RegisterTest {
    private WebDriver driver;
    User user;

    @Before
    public void setup() {
        user = Generator.generateUser();

        driver = WebDriverConfig.setDriver();
        driver.manage().timeouts().implicitlyWait(WebDriverConfig.WAIT_SEC_TIMEOUT, TimeUnit.SECONDS);
        driver.navigate().to(APIconfig.REGISTER_PAGE_URL);
    }

    @Test
    @DisplayName("Register a new user with a short password")
    public void registerNewUserWithShortPasswordGetError() {
        RegisterPage registerPage = new RegisterPage(driver);

        setName(registerPage, user.getName());
        setEmail(registerPage, user.getEmail());
        setPassword(registerPage, Generator.generateWrongUserPassword());
        clickRegister(registerPage);

        WebDriverWait wait = new WebDriverWait(driver, 10); // Исправлено: используем секунды вместо Duration
        wait.until(ExpectedConditions.visibilityOfElementLocated(registerPage.getTextInvalidPasswordLocator()));

        MatcherAssert.assertThat(registerPage.getInvalidPasswordText(), equalTo("Некорректный пароль"));
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

    @Step("Ввод имени: {name}")
    private void setName(RegisterPage registerPage, String name) {
        registerPage.setName(name);
    }

    @Step("Ввод email: {email}")
    private void setEmail(RegisterPage registerPage, String email) {
        registerPage.setEmail(email);
    }

    @Step("Ввод пароля: {password}")
    private void setPassword(RegisterPage registerPage, String password) {
        registerPage.setPassword(password);
    }

    @Step("Нажатие кнопки регистрации")
    private void clickRegister(RegisterPage registerPage) {
        registerPage.clickRegisterButton();
    }
}