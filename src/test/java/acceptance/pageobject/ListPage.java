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

    @FindBy(how = How.XPATH, using = "//tr[@class='ng-scope']/td[1]")
    List<WebElement> bookList;

    @FindBy(how = How.XPATH, using = "//*[@id=\"bs-example-navbar-collapse-1\"]/ul[1]/li[2]/a")
    WebElement listAllButton;

    public List<WebElement> getListOfBooks() {
        return bookList;
    }

    public void clickOnListAllButton(){
        listAllButton.click();
    }


}
