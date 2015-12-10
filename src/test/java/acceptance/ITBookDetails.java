package acceptance;

import acceptance.pageobject.BookDetailsPage;
import acceptance.pageobject.ListPage;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class ITBookDetails extends BaseAcceptance {

    private ListPage listPage;
    private BookDetailsPage bookDetailsPage;
    private static WebDriver driver = getDriver();

    @Before
    public void setup() throws InterruptedException {
        driver.get(baseUrl);

        listPage = new ListPage(driver);
        bookDetailsPage = new BookDetailsPage(driver);

        login();
        navigateToBook("Clean Code");
    }

    private void navigateToBook(String bookTitle) throws InterruptedException {
        listPage.clickOnListAllButton();
        Thread.sleep(1000);

        listPage.selectFilter("Title");
        listPage.setValueForFilter(bookTitle);
        Thread.sleep(1000);

        listPage.clickOnTheFirstBookFromList();
        Thread.sleep(1000);
    }

    @Test
    public void bookTitleIsListed() {
        Assertions.assertThat(bookDetailsPage.getTitleText()).isEqualTo("Clean Code");
    }

    @Test
    public void bookISBNIsListed() {
        Assertions.assertThat(bookDetailsPage.getIsbnText()).isEqualTo("978-0-13-235088-4");
    }

    @Test
    public void bookAuthorsAreListed() {
        Assertions.assertThat(bookDetailsPage.getAuthorsText()).contains("Robert C. Martin");
    }

    @Test
    public void bookCategoriesAreListed() {
        Assertions.assertThat(bookDetailsPage.getCategoriesText()).contains("Agile, Science");
    }

    @Test
    public void bookPublisherIsListed() {
        Assertions.assertThat(bookDetailsPage.getPublisherText()).contains("Prentice Hall Pearson Education");
    }

    @Test
    public void bookPublishDateIsListed() {
        Assertions.assertThat(bookDetailsPage.getPublishDateText()).contains("2009-06-18");
    }

    @Test
    public void bookPagesIsListed() {
        Assertions.assertThat(bookDetailsPage.getPagesText()).isEqualTo("431");
    }

    @Test
    public void bookDescriptionIsListed() {
        Assertions.assertThat(bookDetailsPage.getDescriptionText()).isNotEmpty();
    }
}
