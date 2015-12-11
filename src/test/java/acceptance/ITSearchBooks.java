package acceptance;

import acceptance.pageobject.ListPage;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class ITSearchBooks extends BaseAcceptance {

    private ListPage listPage;
    private static WebDriver driver = getDriver();

    @Before
    public void setup() throws InterruptedException {
        driver.get(baseUrl);

        listPage = new ListPage(driver);

        login();
    }

    @Test
    public void whenSearchBookByTitle_TheBookListIsUpdatedWithTheCorrectResults() throws InterruptedException {
        listPage.clickOnListAllButton();

        listPage.selectFilter("Title");
        listPage.setValueForFilter("The Pragmatic Programmer");
        sleepABit();

        Assertions.assertThat(listPage.getListOfBooks().size()).isEqualTo(1);
    }

    @Test
    public void whenSearchBookByTitle_TheUrlIsUpdatedCorrectly() throws InterruptedException {
        listPage.clickOnListAllButton();
        sleepABit();
        listPage.selectFilter("Title");
        listPage.setValueForFilter("The Pragmatic Programmer");
        sleepABit();

        Assertions.assertThat(driver.getCurrentUrl()).contains("title=The%20Pragmatic%20Programmer");
    }

    @Test
    public void whenSearchBookByIsbn_TheBookListIsUpdatedWithTheCorrectResults() throws InterruptedException {
        listPage.clickOnListAllButton();
        sleepABit();
        listPage.selectFilter("ISBN");
        sleepABit();
        listPage.setValueForFilter("978-0-201-48567-7");
        sleepABit();

        Assertions.assertThat(listPage.getListOfBooks().size()).isEqualTo(1);
    }

    @Test
    public void whenSearchBookByIsbn_TheUrlIsUpdatedCorrectly() throws InterruptedException {
        listPage.clickOnListAllButton();
        sleepABit();
        listPage.selectFilter("ISBN");
        sleepABit();
        listPage.setValueForFilter("978-0-201-48567-7");
        sleepABit();

        Assertions.assertThat(driver.getCurrentUrl()).contains("isbn=978-0-201-48567-7");
    }

}
