package acceptance;

import acceptance.pageobject.AddMemberPage;
import acceptance.pageobject.MemberDetailsPage;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.time.LocalDate;

public class ITMemberDetails  extends BaseAcceptance {

    private static AddMemberPage addMemberPagePage;
    private static MemberDetailsPage memberDetailsPage;
    private static WebDriver driver = getDriver();

    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String BIRTH_DATE = "2015-12-31";
    private static final String ADDRESS = "Default address";
    private static final String NATIONAL_NUMBER = String.valueOf(RandomStringUtils.randomNumeric(12));
    private static final String POSTAL_CODE = "1234";
    private static final String CITY = "Default city";
    private static final String EMAIL = "default@email.com";
    private static final String PHONE_NUMBER = "123456789";

    @BeforeClass
    public static void setup() throws InterruptedException {
        driver.get(baseUrl);

        login();

        addMemberPagePage = new AddMemberPage(driver);
        memberDetailsPage = new MemberDetailsPage(driver);
        addMemberPagePage.clickOnAddMemberButton();
        sleepABit();

        addANewMember();
    }

    private static void addANewMember() {
        addMemberPagePage.inputTextIntoFirstNameField(FIRST_NAME);
        addMemberPagePage.inputTextIntoLastNameField(LAST_NAME);
        addMemberPagePage.inputTextIntoBirthDateField(BIRTH_DATE);
        addMemberPagePage.inputTextIntoAddressField(ADDRESS);
        addMemberPagePage.inputTextIntoNationalNumberField(NATIONAL_NUMBER);
        addMemberPagePage.inputTextIntoPostalCodeField(POSTAL_CODE);
        addMemberPagePage.inputTextIntoCityField(CITY);
        addMemberPagePage.inputTextIntoEmailField(EMAIL);
        addMemberPagePage.inputTextIntoPhoneNumberField(PHONE_NUMBER);
        addMemberPagePage.clickOnSubmitButton();
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
}
