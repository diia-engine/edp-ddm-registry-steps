package platform.qa.officer.pages;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static org.openqa.selenium.By.xpath;

import io.qameta.allure.Step;
import lombok.Getter;
import platform.qa.officer.panel.OfficerHeaderPanel;

import com.codeborne.selenide.SelenideElement;

public class AvailableServicesPage extends OfficerBasePage {

    @Getter
    private final OfficerHeaderPanel headerPanel = new OfficerHeaderPanel();

    private final String availableServices = "Доступні послуги";
    private final SelenideElement availableServicesHeader =
            $(xpath("//div[@data-xpath='header']/following-sibling" + "::div//h1"));

    public AvailableServicesPage() {
        loadingPage();
        loadingComponents();
        checkAvailableServicesHeader();
    }

    SelenideElement getProcessPath(String text) {
        return $(xpath(String.format("//button[text()=\"%s\"]", text)));
    }

    @Step("Сторінка має хедер 'Доступні послуги'")
    public AvailableServicesPage checkAvailableServicesHeader() {
        availableServicesHeader.shouldHave(text(availableServices));
        return this;
    }

    @Step("Натискання на посилання процесу {processName}")
    public void clickOnProcessByName(String processName) {
        getProcessPath(processName)
                .shouldBe(visible)
                .click();
    }

    @Step("Перевірка наявності процесу {processName}")
    public AvailableServicesPage checkProcessByName(String processName) {
        getProcessPath(processName).shouldBe(visible);
        return page(new AvailableServicesPage());
    }

}
