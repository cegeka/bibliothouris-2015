package acceptance;

import acceptance.pageobject.AddMemberPage;
import acceptance.pageobject.ListMemberPage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class ITMemberList extends BaseAcceptance {

    private static AddMemberPage addMemberPage;
    private static ListMemberPage listMemberPage;
    private static WebDriver driver = getDriver();

    @BeforeClass
    public static void setupClass() throws InterruptedException{
        driver.get(baseUrl);

        login();

        addMemberPage = new AddMemberPage(driver);
        listMemberPage = new ListMemberPage(driver);

        addMemberPage.clickOnMembersDropdownButton();
        sleepABit(500);
        addMemberPage.clickOnAddMemberButton();
        sleepABit(500);
        addANewMember(addMemberPage);
        sleepABit(500);

        listMemberPage.clickOnMembersDropdownButton();
        sleepABit(500);
        listMemberPage.clickOnListAllButton();
        sleepABit(500);
    }

    @Before
    public void setup() throws InterruptedException{
        listMemberPage.clearValueForFilter();
        sleepABit(500);
    }

    @Test
    public void membersAreListed() throws InterruptedException {
        assertThat(listMemberPage.getListOfMembers()).isNotEmpty();
    }

    @Test
    public void whenSearchMemberByName_TheListIsUpdatedWithTheCorrectResults() throws InterruptedException {
        listMemberPage.setValueForFilter(FIRST_NAME + " " + LAST_NAME);
        sleepABit(500);

        assertThat(listMemberPage.getFirstMemberName()).contains(FIRST_NAME + " " + LAST_NAME);
    }

    @Test
    public void whenSearchMemberByName_TheUrlIsUpdatedCorrectly() throws InterruptedException {
        listMemberPage.setValueForFilter(FIRST_NAME);
        sleepABit(500);

        assertThat(driver.getCurrentUrl()).contains("name=" + FIRST_NAME);
    }
}
