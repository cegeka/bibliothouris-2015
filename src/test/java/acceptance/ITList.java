package acceptance;

import acceptance.pageobject.ListBookPage;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class ITList extends BaseAcceptance {

    private ListBookPage listBookPage;
    private static WebDriver driver = getDriver();

    @Before
    public void setup() {
        driver.get(baseUrl);

        listBookPage = new ListBookPage(driver);

        login();
    }

    @Test
    public void booksAreListed() throws InterruptedException {
        listBookPage.clickOnListAllButton();
        sleepABit();

        Assertions.assertThat(listBookPage.getListOfBooks()).isNotEmpty();
    }

}
