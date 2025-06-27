import io.qameta.allure.junit4.DisplayName;
import org.example.API.*;
import org.example.Pages.RegisterPage;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.hamcrest.CoreMatchers.equalTo;

public class RegisterTest {
    private WebDriver driver;
    User user;

    @Before
    public void setup() {
        user = Generator.generateUser();

        driver = WebDriverConfig.setDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(WebDriverConfig.WAIT_SEC_TIMEOUT));
        driver.navigate().to(APIconfig.REGISTER_PAGE_URL);
    }

    @Test
    @DisplayName("Register a new user with a short password")
    public void registerNewUserWithShortPasswordGetError() {
        RegisterPage registerPage = new RegisterPage(driver);

        registerPage.setName(user.getName());
        registerPage.setEmail(user.getEmail());
        registerPage.setPassword(Generator.generateWrongUserPassword());
        registerPage.clickRegisterButton();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(registerPage.textInvalidPassword));

        MatcherAssert.assertThat(registerPage.getInvalidPasswordText(), equalTo("Некорректный пароль"));
    }

    @After
    public void teardown() {
        UserOperations.deleteUser(UserOperations.getAccessToken(user));
        driver.quit();
    }
}
