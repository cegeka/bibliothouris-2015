package acceptance.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class AddMemberPage {

    public AddMemberPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.ID, using = "firstName")
    WebElement inputFirstName;

    @FindBy(how = How.ID, using = "lastName")
    WebElement inputLastName;

    @FindBy(how = How.ID, using = "birthdate")
    WebElement inputBirthDate;

    @FindBy(how = How.ID, using = "nationalNumber")
    WebElement inputNationalNumber;

    @FindBy(how = How.ID, using = "address")
    WebElement inputAddress;

    @FindBy(how = How.ID, using = "postalCode")
    WebElement inputPostalCode;

    @FindBy(how = How.ID, using = "city")
    WebElement inputCity;

    @FindBy(how = How.ID, using = "phoneNumber")
    WebElement inputPhoneNumber;

    @FindBy(how = How.ID, using = "email")
    WebElement inputEmail;

    @FindBy(how = How.ID, using = "submit")
    WebElement submitButton;

    @FindBy(how = How.ID, using = "firstNameRequiredMsg")
    WebElement firstNameRequiredMessage;

    @FindBy(how = How.ID, using = "lastNameRequiredMsg")
    WebElement lastNameRequiredMessage;

    @FindBy(how = How.ID, using = "birthdateRequiredMsg")
    WebElement birthDateRequiredMessage;

    @FindBy(how = How.ID, using = "birthdateInvalidMsg")
    WebElement birthDateInvalidMessage;

    @FindBy(how = How.ID, using = "nationalNumberRequiredMsg")
    WebElement nationalNumberRequiredMessage;

    @FindBy(how = How.ID, using = "addMember")
    WebElement addMemberButton;

    @FindBy(how = How.ID, using = "membersDropdown")
    WebElement membersDropdown;

    public void inputTextIntoFirstNameField(String firstName){
        inputFirstName.sendKeys(firstName);
    }

    public void inputTextIntoLastNameField(String lastName){
        inputLastName.sendKeys(lastName);
    }

    public void inputTextIntoBirthDateField(String birthDate){
        inputBirthDate.sendKeys(birthDate);
    }

    public void inputTextIntoNationalNumberField(String nationalNumber){
        inputNationalNumber.sendKeys(nationalNumber);
    }

    public void inputTextIntoAddressField(String address){
        inputAddress.sendKeys(address);
    }

    public void inputTextIntoPostalCodeField(String postalCode){
        inputPostalCode.sendKeys(postalCode);
    }

    public void inputTextIntoCityField(String city){
        inputCity.sendKeys(city);
    }

    public void inputTextIntoPhoneNumberField(String phoneNumber){
        inputPhoneNumber.sendKeys(phoneNumber);
    }

    public void inputTextIntoEmailField(String email){
        inputEmail.sendKeys(email);
    }

    public void clickOnSubmitButton(){
        submitButton.click();
    }

    public void clickOnAddMemberButton(){
        addMemberButton.click();
    }

    public void clickOnMembersDropdownButton(){
        membersDropdown.click();
    }

    public String getFirstNameRequiredMessage(){
        return firstNameRequiredMessage.getText();
    }

    public String getLastNameRequiredMessage(){
        return lastNameRequiredMessage.getText();
    }

    public String getBirthDateRequiredMessage(){
        return birthDateRequiredMessage.getText();
    }

    public String getBirthDateInvalidMessage(){
        return birthDateInvalidMessage.getText();
    }

    public String getNationalNumberRequiredMessage(){
        return nationalNumberRequiredMessage.getText();
    }
}
