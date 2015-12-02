package acceptance;

import org.junit.AfterClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseAcceptance {

    protected String baseUrl = "http://localhost:8090/";
    private static final WebDriver driver = new FirefoxDriver();

    public static WebDriver getDriver(){
        return driver;
    }

    public static void tearDown() {
        driver.quit();
    }


}
