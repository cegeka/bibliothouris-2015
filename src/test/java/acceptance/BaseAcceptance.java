package acceptance;

import acceptance.pageobject.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseAcceptance {

    protected String baseUrl = "http://localhost:8080/";
    private static final WebDriver driver = new FirefoxDriver();

    public static WebDriver getDriver(){
        return driver;
    }

    protected void login() {
        LoginPage login = new LoginPage(driver);

        login.inputTextIntoUsernameField("admin");
        login.inputTextIntoPasswordField("admin");

        login.clickOnLoginButton();
    }

    public static void tearDown() {
        driver.quit();
    }
}
