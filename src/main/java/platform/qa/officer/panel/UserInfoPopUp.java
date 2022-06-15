package platform.qa.officer.panel;

import platform.qa.base.BasePage;
import platform.qa.officer.pages.LoginPage;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class UserInfoPopUp extends BasePage {

    @FindBy(xpath = "//li[@data-xpath='logoutButton']")
    private WebElement logOutButton;

    public LoginPage clickLogOutButton() {
        wait.until(ExpectedConditions.visibilityOf(logOutButton));
        return new LoginPage();
    }
}
