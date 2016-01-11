package acceptance.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ImportBooksPage {

    private WebDriverWait wait;

    public ImportBooksPage(WebDriver driver, WebDriverWait wait) {
        PageFactory.initElements(driver, this);
        this.wait = wait;
    }

    @FindBy(how = How.ID, using = "bookRow")
    List<WebElement> bookList;

    @FindBy(how = How.ID, using = "importBook")
    WebElement importBookButton;

    @FindBy(how = How.ID, using = "booksDropdown")
    WebElement booksDropdownButton;

    @FindBy(how = How.ID, using = "filter")
    WebElement filterSelector;

    @FindBy(how = How.ID, using = "filterValue")
    WebElement filterInput;

    @FindBy(how = How.ID, using = "notification")
    WebElement notification;

    @FindBy(how = How.ID, using = "importModal")
    WebElement formModal;

    @FindBy(how = How.ID, using = "firstName")
    WebElement inputFirstName;

    @FindBy(how = How.ID, using = "lastName")
    WebElement inputLastName;

    @FindBy(how = How.ID, using = "isbnPrefix")
    WebElement inputIsbnPrefix;

    @FindBy(how = How.ID, using = "isbnRegistrationGroup")
    WebElement inputIsbnRegistrationGroupElement;

    @FindBy(how = How.ID, using = "isbnRegistrant")
    WebElement inputIsbnRegistrantElement;

    @FindBy(how = How.ID, using = "isbnPublication")
    WebElement inputIsbnPublicationElement;

    @FindBy(how = How.ID, using = "isbnCheckDigit")
    WebElement inputIsbnCheckDigit;

    @FindBy(how = How.ID, using = "submit")
    WebElement submitButton;

    public List<WebElement> getListOfBooks() {
//        wait.until(ExpectedConditions.visibilityOfAllElements(bookList));
        return bookList;
    }

    public void clickOnImportBookButton() {
//        wait.until(ExpectedConditions.visibilityOf(importBookButton));
        importBookButton.click();
    }

    public void clickOnBooksDropdownButton(){
//        wait.until(ExpectedConditions.visibilityOf(booksDropdownButton));
        booksDropdownButton.click();
    }

    public void selectFilter(String filterName) {
//        wait.until(ExpectedConditions.visibilityOf(filterSelector));
        Select select = new Select(filterSelector);
        select.selectByVisibleText(filterName);
    }

    public void setValueForFilter(String value) {
//        wait.until(ExpectedConditions.visibilityOf(filterInput));
        filterInput.sendKeys(value);
        filterInput.sendKeys(Keys.ENTER);
    }

    public String getTheFirstBookTitle() {
//        wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElements(bookList)));
        return bookList.get(0).findElement(By.id("title")).getText();
    }

    public String getTheFirstBookISBN() {
//        wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElements(bookList)));
        return bookList.get(0).findElement(By.id("isbn")).getText();
    }

    public void clickOnTheFirstBookWithMissingAuthorsImportButton() {
//        wait.until(ExpectedConditions.visibilityOfAllElements(bookList));
        for (WebElement bookElement : bookList)
            if (bookElement.findElement(By.id("authors")).getText().isEmpty() && !bookElement.findElement(By.id("isbn")).getText().isEmpty()) {
                bookElement.findElement(By.id("importButton")).click();
                break;
            }
    }

    public void clickOnTheFirstBookWithMissingISBNImportButton() {
//        wait.until(ExpectedConditions.visibilityOfAllElements(bookList));
        for (WebElement bookElement : bookList)
            if (bookElement.findElement(By.id("isbn")).getText().isEmpty()) {
                bookElement.findElement(By.id("importButton")).click();
                break;
            }
    }

    public void clickOnTheFirstBookImportButton() {
//        wait.until(ExpectedConditions.visibilityOfAllElements(bookList));
        bookList.get(0).findElement(By.id("importButton")).click();
    }

    public boolean isNotificationSuccessfully() {
        wait.until(ExpectedConditions.visibilityOf(notification));

        return notification.getAttribute("class").contains("alert-success");
    }

    public boolean isFormModalVisible() {
        wait.until(ExpectedConditions.visibilityOf(formModal));

        return formModal.isDisplayed();
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

    public void clickOnSubmitButton(){
        submitButton.click();
    }
}
