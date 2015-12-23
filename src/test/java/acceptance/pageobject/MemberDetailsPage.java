package acceptance.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MemberDetailsPage {

    public MemberDetailsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.ID, using = "name")
    WebElement nameOutput;

    @FindBy(how = How.ID, using = "memberSince")
    WebElement memberSinceOutput;

    @FindBy(how = How.ID, using = "address")
    WebElement addressOutput;

    @FindBy(how = How.ID, using = "postalCode")
    WebElement postalCodeOutput;

    @FindBy(how = How.ID, using = "birthDate")
    WebElement birthDateOutput;

    @FindBy(how = How.ID, using = "phone")
    WebElement phoneOutput;

    @FindBy(how = How.ID, using = "email")
    WebElement emailOutput;

    @FindBy(how = How.XPATH, using = "//*[@id=\"availableBookRow\"]/td[4]/button")
    WebElement borrowBookButton;

    @FindBy(how = How.XPATH, using = "//*[@id=\"availableBookRow\"]/td[1]")
    WebElement firstAvailableBookTitle;

    @FindBy(how = How.XPATH, using = "//*[@id=\"borrowedBookRow\"]/td[1]")
    WebElement firstBorrowedBookTitle;

    @FindBy(how = How.XPATH, using = "//*[@id=\"borrowedBookRow\"]/td[5]")
    WebElement firstBorrowedBookEndDate;

    @FindBy(how = How.XPATH, using = "//*[@id=\"borrowedBookRow\"]/td[6]/button")
    WebElement returnBookButton;

    public String getNameText(){
        return nameOutput.getText();
    }

    public String getMemberSinceText(){
        return memberSinceOutput.getText();
    }

    public String getAddressText(){
        return addressOutput.getText();
    }

    public String getPostalCodeText(){
        return postalCodeOutput.getText();
    }

    public String getBirthDateText(){
        return birthDateOutput.getText();
    }

    public String getPhoneText(){
        return phoneOutput.getText();
    }

    public String getEmailText(){
        return emailOutput.getText();
    }

    public void clickOnBorrowButtonForTheFirstAvailableBook() {
        borrowBookButton.click();
    }

    public void clickOnReturnButtonForTheFirstBorrowedBook() {
        returnBookButton.click();
    }

    public String getFirstAvailableBookTitle() {
        return firstAvailableBookTitle.getText();
    }

    public String getFirstBorrowedBookTitle() {
        return firstBorrowedBookTitle.getText();
    }

    public String getFirstBorrowedBookEndDate() {
        return firstBorrowedBookEndDate.getText();
    }
}
