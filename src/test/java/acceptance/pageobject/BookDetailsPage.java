package acceptance.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class BookDetailsPage {
    public BookDetailsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.ID, using = "bookTitle")
    WebElement bookTitle;

    public String getBookTitle(){
        return bookTitle.getText();
    }
}
