package acceptance;

import acceptance.pageobject.ListPage;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class ITList extends BaseAcceptance {

    private ListPage listPage;
    private static WebDriver driver = getDriver();

    @Before
    public void setup() {
        driver.get(baseUrl);

        listPage = new ListPage(driver);

        login();
    }

    @Test
    public void booksAreListed() throws InterruptedException {
        listPage.clickOnListAllButton();
        Thread.sleep(1500);

        Assertions.assertThat(listPage.getListOfBooks()).hasSize(10);
    }

}
