package platform.qa.officer.panel;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import io.qameta.allure.Step;
import platform.qa.officer.pages.AvailableServicesPage;
import platform.qa.officer.pages.DashboardPage;
import platform.qa.officer.pages.MyServicesPage;
import platform.qa.officer.pages.MyTasksPage;
import platform.qa.officer.pages.OfficerBasePage;

import org.openqa.selenium.By;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.conditions.Attribute;


public class OfficerHeaderPanel extends OfficerBasePage {

    SelenideElement headerName = $(By.xpath("//header//div//a[contains(@href,'home')]"));
    SelenideElement mainLink = $(By.xpath("//div[@data-xpath='headerLinks']//a[contains(@href,'home')]"));
    SelenideElement availableServicesLink =
            $(By.xpath("//a[contains(@href,'process-list')]"));
    SelenideElement myServicesLink =
            $(By.xpath("//div[@data-xpath='headerLinks']//a[contains(@href,'process-instance-list')]"));
    SelenideElement myTasksLink = $(By.xpath("//div[@data-xpath='headerLinks']//a[contains(@href,'user-tasks-list')]"));


    public OfficerHeaderPanel() {
        loadingPage();
        loadingComponents();
    }

    @Step("Перевірка хедеру")
    public OfficerHeaderPanel checkHeaderHasLink() {
        headerName.shouldBe(visible).shouldHave(Attribute.attribute("href"));
        return this;
    }

    @Step("Натискання на хедері")
    public DashboardPage clickOnHeaderName() {
        String href = headerName.shouldBe(visible).getAttribute("href");
        WebDriverRunner.getWebDriver().get(href);
        return page(new DashboardPage());
    }

    @Step("Натискання посилання Головна")
    public DashboardPage clickOnMainLink() {
        mainLink.shouldBe(visible).click();
        return page(new DashboardPage());
    }

    @Step("Натискання посилання Мої послуги")
    public MyServicesPage clickOnMyServicesLink() {
        myServicesLink.shouldBe(visible).click();
        return page(new MyServicesPage());
    }

    @Step("Натискання посилання Мої задачі")
    public MyTasksPage clickOnMyTasksLink() {
        myTasksLink.shouldBe(visible).click();
        return page(new MyTasksPage());
    }

    @Step("Натискання посилання Доступні послуги")
    public AvailableServicesPage clickOnAvailableServicesLink() {
        availableServicesLink.shouldBe(visible).click();
        return page(new AvailableServicesPage());
    }
}
