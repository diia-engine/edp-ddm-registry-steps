package platform.qa.officer.pages;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static org.openqa.selenium.By.xpath;

import io.qameta.allure.Step;
import lombok.Getter;
import platform.qa.officer.panel.OfficerHeaderPanel;

import com.codeborne.selenide.SelenideElement;

public class MyServicesPage extends OfficerBasePage {

    @Getter
    private final OfficerHeaderPanel headerPanel = new OfficerHeaderPanel();

    private final String myServicesTextUa = "Мої послуги";
    private final SelenideElement myServicesHeader = $(xpath("//div[@data-xpath='header']/following-sibling::div//h1"));

    public MyServicesPage() {
        loadingPage();
        loadingComponents();
        checkMyServicesHeader();
    }

    @Step("Сторінка має хедер 'Мої послуги'")
    public MyServicesPage checkMyServicesHeader() {
        myServicesHeader.shouldHave(text(myServicesTextUa));
        return this;
    }
}
