package acceptance;

import acceptance.pageobject.ListBookPage;
import acceptance.pageobject.ListMemberPage;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class ITList extends BaseAcceptance {

    private ListBookPage listBookPage;
    private ListMemberPage listMemberPage;
    private static WebDriver driver = getDriver();

    @Before
    public void setup() {
        driver.get(baseUrl);

        listBookPage = new ListBookPage(driver);
        listMemberPage = new ListMemberPage(driver);

        login();
    }

    @Test
    public void booksAreListed() throws InterruptedException {
        listBookPage.clickOnBooksDropdownButton();
        sleepABit();
        listBookPage.clickOnListAllButton();
        sleepABit();

        Assertions.assertThat(listBookPage.getListOfBooks()).isNotEmpty();
    }

    @Test
    public void membersAreListed() throws InterruptedException {
        listMemberPage.clickOnMembersDropdownButton();
        sleepABit();
        listMemberPage.clickOnListAllButton();
        sleepABit();

        Assertions.assertThat(listMemberPage.getListOfMembers()).isNotEmpty();
    }

}
