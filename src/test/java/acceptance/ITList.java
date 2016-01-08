package acceptance;

import acceptance.pageobject.ListBookPage;
import acceptance.pageobject.ListMemberPage;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

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
        sleepABit(500);
        listBookPage.clickOnListAllButton();
        sleepABit(500);

        assertThat(listBookPage.getListOfBooks()).isNotEmpty();
    }

    @Test
    public void membersAreListed() throws InterruptedException {
        listMemberPage.clickOnMembersDropdownButton();
        sleepABit(500);
        listMemberPage.clickOnListAllButton();
        sleepABit(500);

        assertThat(listMemberPage.getListOfMembers()).isNotEmpty();
    }

}
