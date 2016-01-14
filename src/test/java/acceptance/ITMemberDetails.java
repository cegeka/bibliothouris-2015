package acceptance;

import acceptance.pageobject.AddMemberPage;
import acceptance.pageobject.MemberDetailsPage;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class ITMemberDetails  extends BaseAcceptance {

    private static AddMemberPage addMemberPage;
    private static MemberDetailsPage memberDetailsPage;
    private static WebDriver driver = getDriver();

    @BeforeClass
    public static void setup() throws InterruptedException {
        driver.get(baseUrl);

        login();

        addMemberPage = new AddMemberPage(driver);
        memberDetailsPage = new MemberDetailsPage(driver);

        addMemberPage.clickOnMembersDropdownButton();
        sleepABit(500);
        addMemberPage.clickOnAddMemberButton();
        sleepABit(500);
        addANewMember(addMemberPage);
        sleepABit(500);
    }

    @Test
    public void memberNameIsListed() {
        assertThat(memberDetailsPage.getNameText()).isEqualTo(FIRST_NAME + " " + LAST_NAME);
    }

    @Test
    public void memberRegistrationDateIsListed() {
        assertThat(memberDetailsPage.getMemberSinceText()).isEqualTo(LocalDate.now().toString());
    }

    @Test
    public void memberAddressIsListed() {
        assertThat(memberDetailsPage.getAddressText()).isEqualTo(ADDRESS + ", " + CITY);
    }

    @Test
    public void memberPostalCodeIsListed() {
        assertThat(memberDetailsPage.getPostalCodeText()).isEqualTo(POSTAL_CODE);
    }

    @Test
    public void memberBirthDateIsListed() {
        assertThat(memberDetailsPage.getBirthDateText()).isEqualTo(BIRTH_DATE);
    }

    @Test
    public void memberEmailIsListed() {
        assertThat(memberDetailsPage.getEmailText()).isEqualTo(EMAIL);
    }

    @Test
    public void phoneNumberIsListed() {
        assertThat(memberDetailsPage.getPhoneText()).isEqualTo(PHONE_NUMBER);
    }

    @Test
    public void whenBorrowABook_theBorrowedBookIsAddedToMemberHistoryList() {
        sleepABit(1000);
        String borrowedBookTitle = memberDetailsPage.getFirstAvailableBookTitle();
        sleepABit(1000);

        memberDetailsPage.clickOnBorrowButtonForTheFirstAvailableBook();
        sleepABit(1000);

        assertThat(memberDetailsPage.getFirstBorrowedBookTitle()).isEqualTo(borrowedBookTitle);
    }

    @Test
    public void whenReturnABook_theBorrowedBookIsAddedToMemberHistoryList() {
        sleepABit(1000);
        memberDetailsPage.clickOnBorrowButtonForTheFirstAvailableBook();
        sleepABit(1000);

        memberDetailsPage.clickOnReturnButtonForTheFirstBorrowedBook();
        sleepABit(1000);

        assertThat(memberDetailsPage.getFirstBorrowedBookEndDate()).isNotEqualTo("-");
    }
}
