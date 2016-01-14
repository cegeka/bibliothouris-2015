package acceptance;

import acceptance.pageobject.ImportBooksPage;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.assertj.core.api.Assertions.assertThat;

public class ITImportBooks extends BaseAcceptance {

    private ImportBooksPage importBooksPage;
    private static WebDriver driver = getDriver();
    private static WebDriverWait wait = getWait();

    @Before
    public void setup() {
        driver.get(baseUrl);

        importBooksPage = new ImportBooksPage(driver, wait);

        login();

        importBooksPage.clickOnBooksDropdownButton();
        sleepABit(500);
        importBooksPage.clickOnImportBookButton();
        sleepABit(3000);
    }

    @Test
    public void defaultBooksAreListed() throws InterruptedException {
        assertThat(importBooksPage.getListOfBooks()).isNotEmpty();
    }

    @Test
    public void givenATitle_whenSearchBooksByTitle_TheBookListIsUpdatedWithTheCorrectResults() throws InterruptedException {
        importBooksPage.selectFilter("Title");
        importBooksPage.setValueForFilter("Refactoring");
        sleepABit(2000);

        assertThat(importBooksPage.getTheFirstBookTitle()).contains("Refactoring");
    }

    @Test
    public void givenAnISBN_whenSearchBooksByISBN_TheBookListIsUpdatedWithTheCorrectResults() throws InterruptedException {
        importBooksPage.selectFilter("ISBN");
        importBooksPage.setValueForFilter("9780133065268");
        sleepABit(3000);

        if(importBooksPage.getTheFirstBookTitle().isEmpty()) {
            importBooksPage.selectFilter("Title");
            importBooksPage.selectFilter("ISBN");
            importBooksPage.setValueForFilter("013306526X");
            sleepABit(3000);
        }

        assertThat(importBooksPage.getTheFirstBookTitle()).isEqualTo("Refactoring");
        assertThat(importBooksPage.getTheFirstBookISBN().replace("-", "")).isEqualTo("9780133065268");
    }

    @Test
    public void whenTryToImportABookWithoutAuthors_AModalWithAFormForMissingDetailsAppears() throws InterruptedException {
        importBooksPage.clickOnTheFirstBookWithMissingAuthorsImportButton();

        assertThat(importBooksPage.isFormModalVisible()).isTrue();
    }

    @Test
    public void whenTryToImportABookWithoutISBN_AModalWithAFormForMissingDetailsAppears() throws InterruptedException {
        importBooksPage.clickOnTheFirstBookWithMissingISBNImportButton();

        assertThat(importBooksPage.isFormModalVisible()).isTrue();
    }

    @Test
    public void whenTryToImportABook_ASuccessNotificationAppears() throws InterruptedException {
        importBooksPage.clickOnTheFirstBookImportButton();

        assertThat(importBooksPage.isNotificationSuccessfully()).isTrue();
    }

    @Test
    public void whenTryToImportABookWithoutISBNAndCompleteItInTheModal_ASuccessNotificationAppears() throws InterruptedException {
        importBooksPage.clickOnTheFirstBookWithMissingISBNImportButton();
        importBooksPage.inputTextIntoIsbnPrefix("978");
        importBooksPage.inputTextIntoIsbnRegistrantElement("111");
        importBooksPage.inputTextIntoIsbnRegistrationGroupElement("111");
        importBooksPage.inputTextIntoIsbnPublicationElement("111");
        importBooksPage.inputTextIntoIsbnCheckDigit("1");

        importBooksPage.clickOnSubmitButton();

        assertThat(importBooksPage.isNotificationSuccessfully()).isTrue();
    }

    @Test
    public void whenTryToImportABookWithoutAuthorsAndCompleteThemInTheModal_ASuccessNotificationAppears() throws InterruptedException {
        importBooksPage.clickOnTheFirstBookWithMissingAuthorsImportButton();
        importBooksPage.inputTextIntoFirstNameField("John");
        importBooksPage.inputTextIntoLastNameField("Doe");

        importBooksPage.clickOnSubmitButton();

        assertThat(importBooksPage.isNotificationSuccessfully()).isTrue();
    }

}
