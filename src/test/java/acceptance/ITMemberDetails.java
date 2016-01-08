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

    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String BIRTH_DATE = "2250-12-31";
    private static final String ADDRESS = "Melvin Street";
    private static final String NATIONAL_NUMBER = String.valueOf(RandomStringUtils.randomNumeric(12));
    private static final String POSTAL_CODE = "1234";
    private static final String CITY = "London";
    private static final String EMAIL = "default@email.com";
    private static final String PHONE_NUMBER = "123456789";

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
        addANewMember();
        sleepABit(500);
    }

    private static void addANewMember() {
        addMemberPage.inputTextIntoFirstNameField(FIRST_NAME);
        addMemberPage.inputTextIntoLastNameField(LAST_NAME);
        addMemberPage.inputTextIntoBirthDateField(BIRTH_DATE);
        addMemberPage.inputTextIntoAddressField(ADDRESS);
        addMemberPage.inputTextIntoNationalNumberField(NATIONAL_NUMBER);
        addMemberPage.inputTextIntoPostalCodeField(POSTAL_CODE);
        addMemberPage.inputTextIntoCityField(CITY);
        addMemberPage.inputTextIntoEmailField(EMAIL);
        addMemberPage.inputTextIntoPhoneNumberField(PHONE_NUMBER);
        addMemberPage.clickOnSubmitButton();
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
