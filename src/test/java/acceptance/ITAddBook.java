package acceptance;

import acceptance.pageobject.AddBookPage;
import acceptance.pageobject.LoginPage;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class ITAddBook extends BaseAcceptance {

    private static WebDriver driver = getDriver();
    private AddBookPage addBookPage;

    @Before
    public void setup() {
        driver.get(baseUrl);
        login();
        addBookPage = new AddBookPage(driver);
        addBookPage.clickOnAddBookButton();
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

    @Test
    public void whenWeAddABook_ThenItIsAdded() throws InterruptedException {
        addBookPage.inputTextIntoTitleField("Amintiri din copilarie");
        addBookPage.inputTextIntoIsbnField("111-1111-111-11");
        addBookPage.inputTextIntoLastNameField("Creanga");
        addBookPage.inputTextIntoFirstNameField("Ion");
        addBookPage.clickOnSubmitButton();
        Thread.sleep(2500);
        assertThat(driver.getCurrentUrl().contains("/app/#/status")).isTrue();
    }
}
