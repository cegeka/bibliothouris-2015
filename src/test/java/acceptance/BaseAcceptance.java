package acceptance;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseAcceptance {

    private static final WebDriver driver = new FirefoxDriver();

    public static WebDriver getDriver(){
        return driver;
    }
}
