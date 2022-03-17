package platform.qa.officer.pages;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.By.xpath;

import io.qameta.allure.Step;

import com.codeborne.selenide.SelenideElement;

public class LoginPage extends OfficerBasePage {

    SelenideElement authButton = $(xpath("//button[@data-xpath='loginButton']"));
    String loginUrl = baseUrl + "login";

    @Step("Перейти до сторінки автентифікації з КЕП у кабінеті чиновника")
    public AuthWithCesPage openAuthWithCesPage() {
        openPage()
                .checkThatAuthorizationButtonIsActive()
                .clickAuthorizationButton();
        return page(new AuthWithCesPage());
    }

    @Step("Відкриття стартової сторінки кабінету чиновника")
    public LoginPage openPage() {
        openPage(loginUrl);
        assertThat(url()).as("Поточний URL не збігається з очікуваним").isEqualTo(loginUrl);
        return page(new LoginPage());
    }

    @Step("Натискання кнопки 'Увійти до кабінету'")
    public AuthWithCesPage clickAuthorizationButton() {
        authButton.shouldBe(visible).click();
        return page(new AuthWithCesPage());
    }

    @Step("Перевірка що можливість авторизації наявна та активна на сторінці")
    public LoginPage checkThatAuthorizationButtonIsActive() {
        authButton.shouldBe(enabled);
        return page(new LoginPage());
    }
}
