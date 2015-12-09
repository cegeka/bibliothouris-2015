package acceptance.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ListPage {

    public ListPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.ID, using = "bookRow")
    List<WebElement> bookList;

    @FindBy(how = How.ID, using = "listBooks")
    WebElement listAllButton;

    public List<WebElement> getListOfBooks() {
        return bookList;
    }

    public void clickOnListAllButton(){
        listAllButton.click();
    }
}
