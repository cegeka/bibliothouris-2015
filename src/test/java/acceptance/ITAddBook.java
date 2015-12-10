package acceptance;

import acceptance.pageobject.AddBookPage;
import acceptance.pageobject.BookDetailsPage;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class ITAddBook extends BaseAcceptance {

    private static WebDriver driver = getDriver();
    private AddBookPage addBookPage;
    private BookDetailsPage bookDetailsPage;

    @Before
    public void setup() {
        driver.get(baseUrl);

        login();

        addBookPage = new AddBookPage(driver);
        bookDetailsPage = new BookDetailsPage(driver);
        addBookPage.clickOnAddBookButton();
    }

    @Test
    public void givenBookWithoutTitle_ThenSubmitFormFails() {
        addBookPage.clickOnSubmitButton();

        assertThat(addBookPage.getTitleRequiredMessage()).isNotNull();
    }

    @Test
    public void givenBookWithoutISBN_ThenSubmitFormFails() {
        addBookPage.inputTextIntoTitleField("CleanCode");

        addBookPage.clickOnSubmitButton();

        assertThat(addBookPage.getIsbnRequiredMessage()).isNotNull();
    }

    @Test
    public void givenBookWithoutAuthors_ThenSubmitFormFails() throws InterruptedException {
        addBookPage.inputTextIntoTitleField("CleanCode");
        addBookPage.inputTextIntoIsbnField("111-1111-111-11");

        addBookPage.clickOnSubmitButton();

        Thread.sleep(1000);

        assertThat(addBookPage.getLastNameRequiredMessage()).isNotNull();
    }

    @Test
    public void givenBookWithoutCategory_ThenSubmitFormFails() {
        String title = "CleanCode";
        addBookPage.inputTextIntoTitleField(title);
        String lastName = "Fowler";
        addBookPage.inputTextIntoLastNameField(lastName);

        addBookPage.clickOnSubmitButton();

        assertThat(addBookPage.getCatRequiredMessage()).isEqualTo("You did not select a category");
    }

    @Test
    public void givenBookWithNegativePagesNumber_ThenSubmitFormFails() throws InterruptedException {
        String title = "CleanCode";
        addBookPage.inputTextIntoTitleField(title);
        String lastName = "Fowler";
        addBookPage.inputTextIntoLastNameField(lastName);
        addBookPage.clickOnDefaultCategory();
        addBookPage.inputTextIntoPagesField("-50");
        Thread.sleep(2000);
        addBookPage.clickOnSubmitButton();

        assertThat(addBookPage.getPagesRequiredPositiveNumberMessage()).isEqualTo("You typed a negative pages number");
    }
    //testul de text pages number nu va merge in chrome


    @Test
     public void whenWeAddABook_ThenItIsAdded() throws InterruptedException {
        addBookPage.inputTextIntoTitleField("Amintiri din copilarie");
        addBookPage.inputTextIntoIsbnField("111-1111-111-11");
        addBookPage.inputTextIntoLastNameField("Creanga");
        addBookPage.inputTextIntoFirstNameField("Ion");
        addBookPage.clickOnDefaultCategory();
        //addBookPage.inputTextIntoPublishDateField("2015-12-10");
        addBookPage.clickOnSubmitButton();
        Thread.sleep(2000);

        assertThat(bookDetailsPage.getBookTitle()).isEqualTo("Amintiri din copilarie");
    }

}
