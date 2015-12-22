package acceptance;

import acceptance.pageobject.ListBookPage;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class ITSearchBooks extends BaseAcceptance {

    private ListBookPage listBookPage;
    private static WebDriver driver = getDriver();

    @Before
    public void setup() throws InterruptedException {
        driver.get(baseUrl);

        listBookPage = new ListBookPage(driver);

        login();
    }

    @Test
    public void whenSearchBookByTitle_TheBookListIsUpdatedWithTheCorrectResults() throws InterruptedException {
        listBookPage.clickOnListAllButton();

        listBookPage.selectFilter("Title");
        listBookPage.setValueForFilter("The Pragmatic Programmer");
        sleepABit();

        Assertions.assertThat(listBookPage.getListOfBooks().size()).isEqualTo(1);
    }

    @Test
    public void whenSearchBookByTitle_TheUrlIsUpdatedCorrectly() throws InterruptedException {
        listBookPage.clickOnListAllButton();
        sleepABit();
        listBookPage.selectFilter("Title");
        listBookPage.setValueForFilter("The Pragmatic Programmer");
        sleepABit();

        Assertions.assertThat(driver.getCurrentUrl()).contains("title=The%20Pragmatic%20Programmer");
    }

    @Test
    public void whenSearchBookByIsbn_TheBookListIsUpdatedWithTheCorrectResults() throws InterruptedException {
        listBookPage.clickOnListAllButton();
        sleepABit();
        listBookPage.selectFilter("ISBN");
        sleepABit();
        listBookPage.setValueForFilter("978-0-201-48567-7");
        sleepABit();

        Assertions.assertThat(listBookPage.getListOfBooks().size()).isEqualTo(1);
    }

    @Test
    public void whenSearchBookByIsbn_TheUrlIsUpdatedCorrectly() throws InterruptedException {
        listBookPage.clickOnListAllButton();
        sleepABit();
        listBookPage.selectFilter("ISBN");
        sleepABit();
        listBookPage.setValueForFilter("978-0-201-48567-7");
        sleepABit();

        Assertions.assertThat(driver.getCurrentUrl()).contains("isbn=978-0-201-48567-7");
    }

}
