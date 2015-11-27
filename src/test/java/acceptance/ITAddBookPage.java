package acceptance;

import acceptance.pageobject.AddBookPage;
import acceptance.pageobject.BaseAcceptance;
import acceptance.pageobject.LoginPage;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class ITAddBookPage extends BaseAcceptance {

    private static WebDriver driver = getDriver();
    private AddBookPage addBookPage;

    @Before
    public void setup() {
        driver.get("http://localhost:8080/app/#/books/add");
        login();
        addBookPage = new AddBookPage(driver);
        addBookPage.clickOnAddBookButton();
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

    private void login() {
        LoginPage login = new LoginPage(driver);
        String validPassword = "admin";

        login.inputTextIntoUsernameField(validPassword);
        login.inputTextIntoPasswordField(validPassword);
        login.clickOnLoginButton();
    }

    @Test
    public void givenBookWithoutISBN_ThenSubmitFormFails() {
        String title = "CleanCode";
        addBookPage.inputTextIntoTitleField(title);

        addBookPage.clickOnSubmitButton();

        assertThat(addBookPage.getIsbnRequiredMessage()).isNotNull();
    }
}
