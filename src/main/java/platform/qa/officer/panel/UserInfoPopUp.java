package platform.qa.officer.panel;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static org.openqa.selenium.By.xpath;

import io.qameta.allure.Step;
import platform.qa.base.BasePage;
import platform.qa.officer.pages.LoginPage;

import com.codeborne.selenide.SelenideElement;

public class UserInfoPopUp extends BasePage {

    SelenideElement logOutButton = $(xpath("//li[@data-xpath='logoutButton']"));

    @Step("Натискання кнопки 'Вихiд'")
    public LoginPage clickLogOutButton() {
        logOutButton.shouldBe(visible).click();
        return page(new LoginPage());
    }
}
