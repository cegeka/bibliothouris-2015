package acceptance;

import acceptance.pageobject.ListPage;
import acceptance.pageobject.LoginPage;
import org.assertj.core.api.Assertions;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class ITList extends BaseAcceptance {

    private LoginPage login;
    private ListPage listPage;
    private static WebDriver driver = getDriver();


    @Before
    public void setup() {
        driver.get(baseUrl);
        login = new LoginPage(driver);
        listPage = new ListPage(driver);
        login();

    }

    private void login() {
        String validPassword = "admin";

        login.inputTextIntoUsernameField(validPassword);
        login.inputTextIntoPasswordField(validPassword);
        login.clickOnLoginButton();
    }

    @Test
    public void booksAreListed() throws InterruptedException {
        listPage.clickOnListAllButton();
        Thread.sleep(1500);
        Assertions.assertThat(listPage.getListOfBooks()).hasSize(5);
    }

}
