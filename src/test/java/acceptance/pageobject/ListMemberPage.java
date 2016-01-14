package acceptance.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ListMemberPage {
    public ListMemberPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.ID, using = "memberRow")
    List<WebElement> memberList;

    @FindBy(how = How.ID, using = "listMembers")
    WebElement listAllButton;

    @FindBy(how = How.ID, using = "membersDropdown")
    WebElement membersDropdown;

    @FindBy(how = How.ID, using = "filterValue")
    WebElement filterInput;

    public List<WebElement> getListOfMembers() {
        return memberList;
    }

    public void clickOnListAllButton() {
        listAllButton.click();
    }

    public String getFirstMemberName() {
        return memberList.get(0).findElement(By.id("firstName")).getText() + " " + memberList.get(0).findElement(By.id("lastName")).getText();
    }

    public void clickOnMembersDropdownButton(){
        membersDropdown.click();
    }

    public void setValueForFilter(String value) {
        filterInput.sendKeys(value);
        filterInput.sendKeys(Keys.ENTER);
    }

    public void clearValueForFilter() {
        filterInput.clear();
    }

}
