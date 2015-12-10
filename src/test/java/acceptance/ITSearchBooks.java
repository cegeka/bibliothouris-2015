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
    public void whenSearchBook_TheBookListIsUpdatedWithTheCorrectResults() throws InterruptedException {
        listPage.clickOnListAllButton();
        Thread.sleep(1000);
        listPage.selectTitleFilter();
        listPage.setBookTitleForFilter("The Pragmatic Programmer");
        Thread.sleep(1000);

        Assertions.assertThat(listPage.getListOfBooks().size()).isEqualTo(1);
    }

    @Test
    public void whenSearchBook_TheUrlIsUpdatedCorrectly() throws InterruptedException {
        listPage.clickOnListAllButton();
        Thread.sleep(1000);
        listPage.selectTitleFilter();
        listPage.setBookTitleForFilter("The Pragmatic Programmer");
        Thread.sleep(1000);

        Assertions.assertThat(driver.getCurrentUrl()).contains("title=The%20Pragmatic%20Programmer");
    }

}
