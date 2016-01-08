package acceptance;

import acceptance.pageobject.BookDetailsPage;
import acceptance.pageobject.ListBookPage;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class ITBookDetails extends BaseAcceptance {

    private ListBookPage listBookPage;
    private BookDetailsPage bookDetailsPage;
    private static WebDriver driver = getDriver();

    @Before
    public void setup() throws InterruptedException {
        driver.get(baseUrl);

        listBookPage = new ListBookPage(driver);
        bookDetailsPage = new BookDetailsPage(driver);

        login();
        navigateToBook("Clean Code");
    }

    private void navigateToBook(String bookTitle) throws InterruptedException {
        listBookPage.clickOnBooksDropdownButton();
        sleepABit(500);
        listBookPage.clickOnListAllButton();
        sleepABit(500);

        listBookPage.selectFilter("Title");
        listBookPage.setValueForFilter(bookTitle);
        sleepABit(500);

        listBookPage.clickOnTheFirstBookFromList();
        sleepABit(500);
    }

    @Test
    public void bookTitleIsListed() {
        assertThat(bookDetailsPage.getTitleText()).isEqualTo("Clean Code");
    }

    @Test
    public void bookISBNIsListed() {
        assertThat(bookDetailsPage.getIsbnText()).isEqualTo("978-0-13-235088-4");
    }

    @Test
    public void bookAuthorsAreListed() {
        assertThat(bookDetailsPage.getAuthorsText()).contains("Robert C. Martin");
    }

    @Test
    public void bookCategoriesAreListed() {
        assertThat(bookDetailsPage.getCategoriesText()).contains("Agile, Science");
    }

    @Test
    public void bookPublisherIsListed() {
        assertThat(bookDetailsPage.getPublisherText()).contains("Prentice Hall Pearson Education");
    }

    @Test
    public void bookPublishDateIsListed() {
        assertThat(bookDetailsPage.getPublishDateText()).contains("2009-06-18");
    }

    @Test
    public void bookPagesIsListed() {
        assertThat(bookDetailsPage.getPagesText()).isEqualTo("431");
    }

    @Test
    public void bookDescriptionIsListed() {
        assertThat(bookDetailsPage.getDescriptionText()).isNotEmpty();
    }
}
