package acceptance;

import integration.pageobject.LoginPage;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class ITLoginPage {

    private static final WebDriver driver = new FirefoxDriver();
    private LoginPage login;

    @Before
    public void setup() {
        driver.get("http://localhost:8080/");
        login = new LoginPage(driver);
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

    @Test
    public void givenGoodCredentials_ThenloginIsSuccessful() throws InterruptedException {
        login.inputTextIntoUsernameField("admin");
        login.inputTextIntoPasswordField("admin");
        login.clickOnLoginButton();

        assertThat(driver.getCurrentUrl().contains("/#/status")).isTrue();
    }

    @Test
    public void givenBadCredentials_ThenLoginFails() throws InterruptedException {
        login.inputTextIntoUsernameField("invalidPassword");
        login.inputTextIntoPasswordField("invalidPassword");
        login.clickOnLoginButton();

        assertThat(driver.getTitle().contains("Error report")).isTrue();
    }
}
