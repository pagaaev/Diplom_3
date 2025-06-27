import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.example.api.APIconfig;
import org.example.api.WebDriverConfig;
import org.example.Pages.MainPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class ConstructorTest {
    private WebDriver driver;

    @Before
    @Step("Открываем браузер и переходим на главную страницу")
    public void setup() {
        driver = WebDriverConfig.setDriver();
        driver.manage().timeouts().implicitlyWait(WebDriverConfig.WAIT_SEC_TIMEOUT, TimeUnit.SECONDS);
        driver.navigate().to(APIconfig.MAIN_PAGE_URL);
    }

    @Test
    @DisplayName("Переключение на вкладку 'Булки'")
    public void SwitchToBunsTabGetSuccess() {
        MainPage mainPage = new MainPage(driver);

        clickFillings(mainPage);
        clickBuns(mainPage);

        Assert.assertTrue(mainPage.btnBunsIsEnabled());
    }

    @Step("Кликаем на вкладку 'Начинки'")
    private void clickFillings(MainPage mainPage) {
        mainPage.clickFillingsButton();
    }

    @Step("Кликаем на вкладку 'Булки'")
    private void clickBuns(MainPage mainPage) {
        mainPage.clickBunsButton();
    }

    @Test
    @DisplayName("Переключение на вкладку 'Соусы'")
    public void SwitchToSaucesTabGetSuccess() {
        MainPage mainPage = new MainPage(driver);

        clickSauces(mainPage);

        Assert.assertTrue(mainPage.btnSaucesIsEnabled());
    }

    @Step("Кликаем на вкладку 'Соусы'")
    private void clickSauces(MainPage mainPage) {
        mainPage.clickSaucesButton();
    }

    @Test
    @DisplayName("Переключение на вкладку 'Начинки'")
    public void SwitchToFillingsTabGetSuccess() {
        MainPage mainPage = new MainPage(driver);

        clickFillings(mainPage);

        Assert.assertTrue(mainPage.btnFillingsIsEnabled());
    }

    @After
    @Step("Закрываем браузер")
    public void teardown() {
        driver.quit();
    }
}
