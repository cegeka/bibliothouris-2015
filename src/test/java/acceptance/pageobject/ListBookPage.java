package acceptance.pageobject;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ListBookPage {

    public ListBookPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.ID, using = "bookRow")
    List<WebElement> bookList;

    @FindBy(how = How.ID, using = "listBooks")
    WebElement listAllButton;

    @FindBy(how = How.ID, using = "booksDropdown")
    WebElement booksDropdownButton;

    @FindBy(how = How.ID, using = "filter")
    WebElement filterSelector;

    @FindBy(how = How.ID, using = "filterValue")
    WebElement filterInput;

    public List<WebElement> getListOfBooks() {
        return bookList;
    }

    public void clickOnListAllButton() {
        listAllButton.click();
    }

    public void clickOnBooksDropdownButton(){
        booksDropdownButton.click();
    }

    public void selectFilter(String filterName) {
        Select select = new Select(filterSelector);
        select.selectByVisibleText(filterName);
    }

    public void setValueForFilter(String value) {
        filterInput.sendKeys(value);
        filterInput.sendKeys(Keys.ENTER);
    }

    public void clickOnTheFirstBookFromList() {
        bookList.get(0).click();
    }
}
