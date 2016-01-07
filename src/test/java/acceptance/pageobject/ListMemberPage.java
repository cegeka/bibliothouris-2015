package acceptance.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

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

    public List<WebElement> getListOfMembers() {
        return memberList;
    }

    public void clickOnListAllButton() {
        listAllButton.click();
    }

    public void clickOnTheFirstMemberFromList() {
        memberList.get(0).click();
    }

    public void clickOnMembersDropdownButton(){
        membersDropdown.click();
    }

}
