package acceptance;

import acceptance.pageobject.AddMemberPage;
import acceptance.pageobject.ListMemberPage;
import acceptance.pageobject.MemberDetailsPage;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;

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

        addMemberPage.clickOnAddMemberButton();
        sleepABit();
        addANewMember();
        sleepABit();
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
        sleepABit();
    }

    @Test
    public void memberNameIsListed() {
        Assertions.assertThat(memberDetailsPage.getNameText()).isEqualTo(FIRST_NAME + " " + LAST_NAME);
    }

    @Test
    public void memberRegistrationDateIsListed() {
        Assertions.assertThat(memberDetailsPage.getMemberSinceText()).isEqualTo(LocalDate.now().toString());
    }

    @Test
    public void memberAddressIsListed() {
        Assertions.assertThat(memberDetailsPage.getAddressText()).isEqualTo(ADDRESS + ", " + CITY);
    }

    @Test
    public void memberPostalCodeIsListed() {
        Assertions.assertThat(memberDetailsPage.getPostalCodeText()).isEqualTo(POSTAL_CODE);
    }

    @Test
    public void memberBirthDateIsListed() {
        Assertions.assertThat(memberDetailsPage.getBirthDateText()).isEqualTo(BIRTH_DATE);
    }

    @Test
    public void memberEmailIsListed() {
        Assertions.assertThat(memberDetailsPage.getEmailText()).isEqualTo(EMAIL);
    }

    @Test
    public void phoneNumberIsListed() {
        Assertions.assertThat(memberDetailsPage.getPhoneText()).isEqualTo(PHONE_NUMBER);
    }

    @Test
    public void whenBorrowABook_theBorrowedBookIsAddedToMemberHistoryList() {
        sleepABit();
        sleepABit();
        String borrowedBookTitle = memberDetailsPage.getFirstAvailableBookTitle();
        sleepABit();

        memberDetailsPage.clickOnBorrowButtonForTheFirstAvailableBook();
        sleepABit();

        Assertions.assertThat(memberDetailsPage.getFirstBorrowedBookTitle()).isEqualTo(borrowedBookTitle);
    }

    @Test
    public void whenReturnABook_theBorrowedBookIsAddedToMemberHistoryList() {
        sleepABit();
        memberDetailsPage.clickOnBorrowButtonForTheFirstAvailableBook();
        sleepABit();

        memberDetailsPage.clickOnReturnButtonForTheFirstBorrowedBook();
        sleepABit();

        Assertions.assertThat(memberDetailsPage.getFirstBorrowedBookEndDate()).isNotEqualTo("-");
    }
}
