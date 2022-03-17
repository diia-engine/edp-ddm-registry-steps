package platform.qa.officer.pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import io.qameta.allure.Step;
import lombok.Getter;
import platform.qa.officer.panel.OfficerHeaderPanel;

import org.openqa.selenium.By;
import com.codeborne.selenide.SelenideElement;

public class DashboardPage extends OfficerBasePage {

    @Getter
    private final OfficerHeaderPanel headerPanel = new OfficerHeaderPanel();

    private final String availableServices = "Доступні послуги";
    private final String myServices = "Мої послуги";
    private final String myTasks = "Мої задачі";
    private final String reports = "Звіти";

    public DashboardPage() {
        loadingPage();
        loadingComponents();
        checkServices();
    }

    public SelenideElement availableServices(String text) {
        return $(By.xpath(String.format("//div[@data-xpath='processDefinitionMenuOption']//a[contains(text(), '%s')]"
                , text)));
    }

    public SelenideElement myServices(String text) {
        return $(By.xpath(String.format("//div[@data-xpath='processActiveMenuOption']//a[contains(text(), '%s')]",
                text)));
    }

    public SelenideElement myTasks(String text) {
        return $(By.xpath(String.format("//div[@data-xpath='taskMenuOption']//a[contains(text(), '%s')]", text)));
    }

    public SelenideElement reports(String text) {
        return $(By.xpath(String.format("//div[@data-xpath='reportMenuOption']//a[contains(text(), '%s')]", text)));
    }

    @Step("Перевірка наявності головних розділів на Дашборді")
    public DashboardPage checkServices() {
        availableServices(availableServices).shouldBe(visible);
        myServices(myServices).shouldBe(visible);
        myTasks(myTasks).shouldBe(visible);
        reports(reports).shouldBe(visible);
        return this;
    }

    @Step("Натискання на посилання 'Доступні послуги'")
    public AvailableServicesPage clickOnAvailableServices() {
        availableServices(availableServices).shouldBe(visible).click();
        return page(new AvailableServicesPage());
    }
}
