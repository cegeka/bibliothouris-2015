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
        sleepABit();
    }

    @Test
    public void givenABookWithoutTitle_whenTheFormIsSubmitted_ThenSubmitFormFails() {
        addBookPage.clickOnSubmitButton();

        assertThat(addBookPage.getTitleRequiredMessage()).isNotNull();
    }

    @Test
    public void givenABookWithoutISBN_whenTheFormIsSubmitted_ThenSubmitFormFails() {
        addBookPage.inputTextIntoTitleField("CleanCode");

        addBookPage.clickOnSubmitButton();

        assertThat(addBookPage.getIsbnRequiredMessage()).isNotNull();
    }

    @Test
    public void givenABookWithoutAuthors_whenTheFormIsSubmitted_ThenSubmitFormFails() throws InterruptedException {
        addBookPage.inputTextIntoTitleField("CleanCode");
        addBookPage.inputTextIntoIsbnPrefix("978");
        addBookPage.inputTextIntoIsbnRegistrantElement("111");
        addBookPage.inputTextIntoIsbnRegistrationGroupElement("111");
        addBookPage.inputTextIntoIsbnPublicationElement("111");
        addBookPage.inputTextIntoIsbnCheckDigit("1");

        addBookPage.clickOnSubmitButton();

        sleepABit();

        assertThat(addBookPage.getLastNameRequiredMessage()).isNotNull();
    }

    @Test
    public void givenABookWithoutCategory_whenTheFormIsSubmitted_ThenSubmitFormFails() {
        String title = "CleanCode";
        addBookPage.inputTextIntoTitleField(title);
        String lastName = "Fowler";
        addBookPage.inputTextIntoLastNameField(lastName);

        addBookPage.clickOnSubmitButton();

        assertThat(addBookPage.getCatRequiredMessage()).isEqualTo("You did not select a category");
    }

    @Test
    public void givenABookWithNegativePagesNumber_whenTheFormIsSubmitted_ThenSubmitFormFails() throws InterruptedException {
        String title = "CleanCode";
        addBookPage.inputTextIntoTitleField(title);
        String lastName = "Fowler";
        addBookPage.inputTextIntoLastNameField(lastName);
        addBookPage.clickOnDefaultCategory();
        addBookPage.inputTextIntoPagesField("-50");
        sleepABit();
        addBookPage.clickOnSubmitButton();

        assertThat(addBookPage.getPagesRequiredPositiveNumberMessage()).isEqualTo("You typed a negative pages number");
    }

    @Test
    public void givenABookWithTextPagesNumber_whenTheFormIsSubmitted_ThenSubmitFormFails() throws InterruptedException {
        String title = "CleanCode";
        addBookPage.inputTextIntoTitleField(title);
        String lastName = "Fowler";
        addBookPage.inputTextIntoLastNameField(lastName);
        addBookPage.clickOnDefaultCategory();
        addBookPage.inputTextIntoPagesField("aaa");

        addBookPage.clickOnSubmitButton();
        sleepABit();

        assertThat(addBookPage.getPagesRequiredNumberMessage()).isEqualTo("You typed a text page number");
    }

    @Test
    public void givenAValidBook_whenTheFormIsSubmitted_ThenItIsAdded() throws InterruptedException {
        addBookPage.inputTextIntoTitleField("Amintiri din copilarie");
        addBookPage.inputTextIntoIsbnPrefix("978");
        addBookPage.inputTextIntoIsbnRegistrantElement("111");
        addBookPage.inputTextIntoIsbnRegistrationGroupElement("111");
        addBookPage.inputTextIntoIsbnPublicationElement("111");
        addBookPage.inputTextIntoIsbnCheckDigit("1");
        addBookPage.inputTextIntoLastNameField("Creanga");
        addBookPage.inputTextIntoFirstNameField("Ion");
        addBookPage.clickOnDefaultCategory();
        //addBookPage.inputTextIntoPublishDateField("2015-12-10");

        addBookPage.clickOnSubmitButton();
        sleepABit();

        assertThat(bookDetailsPage.getTitleText()).isEqualTo("Amintiri din copilarie");
    }

    @Test
    public void givenABookWithInvalidIsbn_whenTheFormIsSubmitted_ThenSubmitFormFails() throws InterruptedException {
        addBookPage.inputTextIntoTitleField("Amintiri din copilarie");
        addBookPage.inputTextIntoIsbnPrefix("978");
        addBookPage.inputTextIntoIsbnRegistrantElement("111");
        addBookPage.inputTextIntoIsbnRegistrationGroupElement("111");
        addBookPage.inputTextIntoIsbnPublicationElement("11");
        addBookPage.inputTextIntoIsbnCheckDigit("1");
        addBookPage.inputTextIntoLastNameField("Creanga");
        addBookPage.inputTextIntoFirstNameField("Ion");
        addBookPage.clickOnDefaultCategory();

        addBookPage.clickOnSubmitButton();
        sleepABit();

        assertThat(addBookPage.getIsbnInvalidMessage()).isEqualTo("You did not enter a valid ISBN code");
    }

}
