package acceptance.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class AddBookPage {

    public AddBookPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.ID, using = "title")
    WebElement inputTitle;

    @FindBy(how = How.ID, using = "firstName")
    WebElement inputFirstName;

    @FindBy(how = How.ID, using = "lastName")
    WebElement inputLastName;

    @FindBy(how = How.ID, using = "isbnPrefix")
    WebElement inputIsbnPrefix;

    @FindBy(how = How.ID, using = "isbnRegistrationGroupElement")
    WebElement inputIsbnRegistrationGroupElement;

    @FindBy(how = How.ID, using = "isbnRegistrantElement")
    WebElement inputIsbnRegistrantElement;

    @FindBy(how = How.ID, using = "isbnPublicationElement")
    WebElement inputIsbnPublicationElement;

    @FindBy(how = How.ID, using = "isbnCheckDigit")
    WebElement inputIsbnCheckDigit;

    @FindBy(how = How.ID, using = "description")
    WebElement inputDescription;

    @FindBy(how = How.ID, using = "Agile")
    WebElement defaultCategory;

    @FindBy(how = How.ID, using = "pages")
    WebElement inputPages;

    @FindBy(how = How.ID, using = "publishDate")
    WebElement inputDate;

    @FindBy(how = How.ID, using = "publisher")
    WebElement inputPublisher;

    @FindBy(how = How.ID, using = "submit")
    WebElement submitButton;

    @FindBy(how = How.ID, using = "isbnRequiredMsg")
    WebElement isbnRequiredMessage;

    @FindBy(how = How.ID, using = "isbnInvalidMsg")
    WebElement isbnInvalidMessage;

    @FindBy(how = How.ID, using = "titleRequiredMsg")
    WebElement titleRequiredMessage;

    @FindBy(how = How.ID, using = "categoryRequiredMsg")
    WebElement catRequiredMessage;

    @FindBy(how = How.ID, using = "lastNameRequiredMsg")
    WebElement lastNameRequiredMessage;

    @FindBy(how = How.ID, using = "pagesRequiredNumberMsg")
    WebElement pagesRequiredNumberMessage;

    @FindBy(how = How.ID, using = "pagesRequiredPositiveNumberMsg")
    WebElement pagesRequiredPositiveNumberMessage;

    @FindBy(how = How.ID, using = "addBook")
    WebElement addBookButton;

    public void inputTextIntoTitleField(String title){
        inputTitle.sendKeys(title);
    }

    public void inputTextIntoFirstNameField(String firstName){
        inputFirstName.sendKeys(firstName);
    }

    public void inputTextIntoLastNameField(String lastName){
        inputLastName.sendKeys(lastName);
    }

    public void inputTextIntoIsbnPrefix(String isbnPrefix){
        inputIsbnPrefix.sendKeys(isbnPrefix);
    }

    public void inputTextIntoIsbnRegistrationGroupElement(String isbnRegistrationGroupElement){
        inputIsbnRegistrationGroupElement.sendKeys(isbnRegistrationGroupElement);
    }

    public void inputTextIntoIsbnRegistrantElement(String isbnRegistrantElement){
        inputIsbnRegistrantElement.sendKeys(isbnRegistrantElement);
    }

    public void inputTextIntoIsbnPublicationElement(String isbnPublicationElement){
        inputIsbnPublicationElement.sendKeys(isbnPublicationElement);
    }

    public void inputTextIntoIsbnCheckDigit(String isbnCheckDigit){
        inputIsbnCheckDigit.sendKeys(isbnCheckDigit);
    }

    public void inputTextIntoDescriptionField(String description){
        inputDescription.sendKeys(description);
    }

    public void inputTextIntoPagesField(String pages){
        inputPages.sendKeys(pages);
    }

    public void inputTextIntoPublishDateField(String date){
        inputDate.sendKeys(date);
    }

    public void inputTextIntoPublisherField(String publisher){
        inputPublisher.sendKeys(publisher);
    }

    public void clickOnSubmitButton(){
        submitButton.click();
    }

    public void clickOnAddBookButton(){
        addBookButton.click();
    }

    public void clickOnDefaultCategory(){
        defaultCategory.click();
    }

    public String getIsbnRequiredMessage(){
        return isbnRequiredMessage.getText();
    }

    public String getIsbnInvalidMessage(){
        return isbnInvalidMessage.getText();
    }

    public String getTitleRequiredMessage(){
        return titleRequiredMessage.getText();
    }

    public String getLastNameRequiredMessage(){
        return lastNameRequiredMessage.getText();
    }

    public String getCatRequiredMessage(){
        return catRequiredMessage.getText();
    }

    public String getPagesRequiredNumberMessage(){
        return pagesRequiredNumberMessage.getText();
    }

    public String getPagesRequiredPositiveNumberMessage(){
        return pagesRequiredPositiveNumberMessage.getText();
    }

}
