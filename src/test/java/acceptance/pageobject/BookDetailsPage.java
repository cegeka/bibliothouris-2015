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

    @FindBy(how = How.ID, using = "title")
    WebElement titleOutput;

    @FindBy(how = How.ID, using = "authors")
    WebElement authorsOutput;

    @FindBy(how = How.ID, using = "isbn")
    WebElement isbnOutput;

    @FindBy(how = How.ID, using = "categories")
    WebElement categoriesOutput;

    @FindBy(how = How.ID, using = "publisher")
    WebElement publisherOutput;

    @FindBy(how = How.ID, using = "pages")
    WebElement pagesOutput;

    @FindBy(how = How.ID, using = "publishDate")
    WebElement publishDateOutput;

    @FindBy(how = How.ID, using = "description")
    WebElement descriptionOutput;

    public String getTitleText(){
        return titleOutput.getText();
    }

    public String getAuthorsText(){
        return authorsOutput.getText();
    }

    public String getIsbnText(){
        return isbnOutput.getText();
    }

    public String getCategoriesText(){
        return categoriesOutput.getText();
    }

    public String getPublisherText(){
        return publisherOutput.getText();
    }

    public String getPublishDateText(){
        return publishDateOutput.getText();
    }

    public String getPagesText(){
        return pagesOutput.getText();
    }

    public String getDescriptionText(){
        return descriptionOutput.getText();
    }

}
