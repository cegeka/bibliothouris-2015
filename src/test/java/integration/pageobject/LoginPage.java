package integration.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.ID, using = "inputUsername")
    WebElement inputUsername;

    @FindBy(how = How.ID, using = "inputPassword")
    WebElement inputPassword;

    @FindBy(how = How.ID, using = "loginButton")
    WebElement loginButton;

    public void inputTextIntoUsernameField(String username){
        inputUsername.sendKeys(username);
    }

    public void inputTextIntoPasswordField(String password){
        inputPassword.sendKeys(password);
    }

    public void clickOnLoginButton(){
        loginButton.click();
    }
}
