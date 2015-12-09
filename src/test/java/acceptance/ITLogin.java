package acceptance;

import acceptance.pageobject.LoginPage;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class ITLogin extends BaseAcceptance{

    private static final WebDriver driver = getDriver();
    private LoginPage login;

    @Before
    public void setup() {
        driver.get(baseUrl);
        login = new LoginPage(driver);
    }

    @Test
    public void givenGoodCredentials_ThenloginIsSuccessful() {
        String validPassword = "admin";

        login.inputTextIntoUsernameField(validPassword);
        login.inputTextIntoPasswordField(validPassword);
        login.clickOnLoginButton();

        assertThat(driver.getCurrentUrl().contains("/#/status")).isTrue();
    }

    @Test
    public void givenBadCredentials_ThenLoginFails() {
        String invalidPassword = "invalidPassword";

        login.inputTextIntoUsernameField(invalidPassword);
        login.inputTextIntoPasswordField(invalidPassword);
        login.clickOnLoginButton();

        assertThat(driver.getCurrentUrl().contains("login.html?error")).isTrue();
    }
}
