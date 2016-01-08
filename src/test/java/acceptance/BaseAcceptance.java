package acceptance;

import acceptance.pageobject.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseAcceptance {

    protected static String baseUrl = "http://localhost:8080/";
    private static final WebDriver driver = new FirefoxDriver();
    private static final WebDriverWait wait = new WebDriverWait(driver, 10);

    public static WebDriver getDriver(){
        return driver;
    }

    public static WebDriverWait getWait() {
        return wait;
    }

    public static void sleepABit(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected static void login() {
        LoginPage login = new LoginPage(driver);

        login.inputTextIntoUsernameField("admin");
        login.inputTextIntoPasswordField("admin");

        login.clickOnLoginButton();
    }

    public static void tearDown() {
        driver.quit();
    }
}
