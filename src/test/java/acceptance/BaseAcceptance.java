package acceptance;

import acceptance.pageobject.AddMemberPage;
import acceptance.pageobject.LoginPage;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseAcceptance {

    protected static String baseUrl = "http://localhost:8080/";
    private static final WebDriver driver = new FirefoxDriver();
    private static final WebDriverWait wait = new WebDriverWait(driver, 10);

    protected static final String FIRST_NAME = "John";
    protected static final String LAST_NAME = "Doe";
    protected static final String BIRTH_DATE = "2250-12-31";
    protected static final String ADDRESS = "Melvin Street";
    protected static final String NATIONAL_NUMBER = String.valueOf(RandomStringUtils.randomNumeric(12));
    protected static final String POSTAL_CODE = "1234";
    protected static final String CITY = "London";
    protected static final String EMAIL = "default@email.com";
    protected static final String PHONE_NUMBER = "123456789";

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

    protected static void addANewMember(AddMemberPage addMemberPage) {
        addMemberPage.inputTextIntoFirstNameField(FIRST_NAME);
        addMemberPage.inputTextIntoLastNameField(LAST_NAME);
        addMemberPage.inputTextIntoBirthDateField(BIRTH_DATE);
        addMemberPage.inputTextIntoAddressField(ADDRESS);
        addMemberPage.inputTextIntoNationalNumberField(NATIONAL_NUMBER);
        addMemberPage.inputTextIntoPostalCodeField(POSTAL_CODE);
        addMemberPage.inputTextIntoCityField(CITY);
        addMemberPage.inputTextIntoEmailField(EMAIL);
        addMemberPage.inputTextIntoPhoneNumberField(PHONE_NUMBER);
        addMemberPage.clickOnSubmitButton();
        sleepABit(500);
    }
}
