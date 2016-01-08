package acceptance;

import acceptance.pageobject.AddMemberPage;
import acceptance.pageobject.MemberDetailsPage;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class ITAddMember extends BaseAcceptance {

    private static WebDriver driver = getDriver();
    private AddMemberPage addMemberPagePage;
    private MemberDetailsPage memberDetailsPage;

    @Before
    public void setup() {
        driver.get(baseUrl);

        login();

        addMemberPagePage = new AddMemberPage(driver);
        memberDetailsPage = new MemberDetailsPage(driver);
        addMemberPagePage.clickOnMembersDropdownButton();
        sleepABit(500);
        addMemberPagePage.clickOnAddMemberButton();
        sleepABit(500);
    }

    @Test
    public void givenAMemberWithoutFirstName_whenTheFormIsSubmitted_ThenSubmitFormFails() {
        addMemberPagePage.clickOnSubmitButton();

        assertThat(addMemberPagePage.getFirstNameRequiredMessage()).isNotNull();
    }

    @Test
    public void givenAMemberWithoutLastName_whenTheFormIsSubmitted_ThenSubmitFormFails() {
        addMemberPagePage.inputTextIntoFirstNameField("John");

        addMemberPagePage.clickOnSubmitButton();

        assertThat(addMemberPagePage.getLastNameRequiredMessage()).isNotNull();
    }

    @Test
    public void givenAMemberWithoutBirthDate_whenTheFormIsSubmitted_ThenSubmitFormFails() {
        addMemberPagePage.inputTextIntoFirstNameField("John");
        addMemberPagePage.inputTextIntoLastNameField("Doe");

        addMemberPagePage.clickOnSubmitButton();

        assertThat(addMemberPagePage.getBirthDateRequiredMessage()).isNotNull();
    }

    @Test
    public void givenAMemberWithAnInvalidBirthDate_whenTheFormIsSubmitted_ThenSubmitFormFails() {
        addMemberPagePage.inputTextIntoFirstNameField("John");
        addMemberPagePage.inputTextIntoLastNameField("Doe");
        addMemberPagePage.inputTextIntoBirthDateField("invalid date format");

        addMemberPagePage.clickOnSubmitButton();

        assertThat(addMemberPagePage.getBirthDateInvalidMessage()).isNotNull();
    }

    @Test
    public void givenAMemberWithoutNationalNumber_whenTheFormIsSubmitted_ThenSubmitFormFails() {
        addMemberPagePage.inputTextIntoFirstNameField("John");
        addMemberPagePage.inputTextIntoLastNameField("Doe");
        addMemberPagePage.inputTextIntoBirthDateField("2015-12-31");

        addMemberPagePage.clickOnSubmitButton();

        assertThat(addMemberPagePage.getNationalNumberRequiredMessage()).isNotNull();
    }

    @Test
    public void givenAValidMember_whenTheFormIsSubmitted_ThenItIsAdded() throws InterruptedException {
        addMemberPagePage.inputTextIntoFirstNameField("John");
        addMemberPagePage.inputTextIntoLastNameField("Doe");
        addMemberPagePage.inputTextIntoBirthDateField("2015-12-31");
        addMemberPagePage.inputTextIntoAddressField("Default adress");
        addMemberPagePage.inputTextIntoNationalNumberField(String.valueOf(RandomStringUtils.randomNumeric(12)));
        addMemberPagePage.inputTextIntoPostalCodeField("0011");
        addMemberPagePage.inputTextIntoCityField("Default city");
        addMemberPagePage.inputTextIntoEmailField("default@email.com");
        addMemberPagePage.inputTextIntoPhoneNumberField("0123456789");

        addMemberPagePage.clickOnSubmitButton();
        sleepABit(500);

        assertThat(memberDetailsPage.getNameText()).isEqualTo("John Doe");
    }
}
