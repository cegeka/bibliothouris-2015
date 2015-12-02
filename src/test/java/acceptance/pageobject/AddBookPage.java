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

    @FindBy(how = How.ID, using = "isbn")
    WebElement inputIsbn;

    @FindBy(how = How.ID, using = "submit")
    WebElement submitButton;

    @FindBy(how = How.XPATH, using = "//*[@id=\"addForm\"]/div[4]/div/div/div")
    WebElement isbnRequiredMessage;


    @FindBy(how = How.XPATH, using = "//*[@id=\"bs-example-navbar-collapse-1\"]/ul[1]/li[1]/a")
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

    public void inputTextIntoIsbnField(String isbn){
        inputIsbn.sendKeys(isbn);
    }

    public void clickOnSubmitButton(){
        submitButton.click();
    }

    public void clickOnAddBookButton(){
        addBookButton.click();
    }

    public String getIsbnRequiredMessage(){
        return isbnRequiredMessage.getText();
    }
}
