package platform.qa.officer.pages;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends OfficerBasePage {

    @FindBy(xpath = "//button[@data-xpath='loginButton']")
    WebElement authButton;
    String loginUrl = baseUrl + "login";

    public AuthWithCesPage openAuthWithCesPage() {
        return openPage()
                .checkThatAuthorizationButtonIsActive()
                .clickAuthorizationButton();
    }

    public LoginPage openPage() {
        openPage(loginUrl);
        wait
                .withMessage("Поточний URL не збігається з очікуваним")
                .until(ExpectedConditions.urlToBe(loginUrl));
        return this;
    }

    public AuthWithCesPage clickAuthorizationButton() {
        wait
                .until(visibilityOf(authButton))
                .click();
        return new AuthWithCesPage();
    }

    public LoginPage checkThatAuthorizationButtonIsActive() {
        wait.until(elementToBeClickable(authButton));
        return this;
    }
}
