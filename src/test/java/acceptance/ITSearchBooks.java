package acceptance;

import acceptance.pageobject.ListBookPage;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class ITSearchBooks extends BaseAcceptance {

    private ListBookPage listBookPage;
    private static WebDriver driver = getDriver();

    @Before
    public void setup() throws InterruptedException {
        driver.get(baseUrl);

        login();

        listBookPage = new ListBookPage(driver);

        listBookPage.clickOnBooksDropdownButton();
        sleepABit(500);
        listBookPage.clickOnListAllButton();
        sleepABit(500);

    }

    @Test
    public void whenSearchBookByTitle_TheBookListIsUpdatedWithTheCorrectResults() throws InterruptedException {
        listBookPage.selectFilter("Title");
        listBookPage.setValueForFilter("The Pragmatic Programmer");
        sleepABit(500);

        assertThat(listBookPage.getListOfBooks().size()).isEqualTo(1);
    }

    @Test
    public void whenSearchBookByTitle_TheUrlIsUpdatedCorrectly() throws InterruptedException {
        listBookPage.selectFilter("Title");
        listBookPage.setValueForFilter("The Pragmatic Programmer");
        sleepABit(500);

        assertThat(driver.getCurrentUrl()).contains("title=The%20Pragmatic%20Programmer");
    }

    @Test
    public void whenSearchBookByIsbn_TheBookListIsUpdatedWithTheCorrectResults() throws InterruptedException {
        listBookPage.selectFilter("ISBN");
        sleepABit(500);
        listBookPage.setValueForFilter("978-0-201-48567-7");
        sleepABit(500);

        assertThat(listBookPage.getListOfBooks().size()).isEqualTo(1);
    }

    @Test
    public void whenSearchBookByIsbn_TheUrlIsUpdatedCorrectly() throws InterruptedException {
        listBookPage.selectFilter("ISBN");
        sleepABit(500);
        listBookPage.setValueForFilter("978-0-201-48567-7");
        sleepABit(500);

        assertThat(driver.getCurrentUrl()).contains("isbn=978-0-201-48567-7");
    }

}
