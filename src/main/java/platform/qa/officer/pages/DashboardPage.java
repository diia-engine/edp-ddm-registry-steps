package platform.qa.officer.pages;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import lombok.Getter;
import platform.qa.officer.panel.OfficerHeaderPanel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

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

    public WebElement availableServices(String text) {
        return driver.findElement(By.xpath(String.format("//div[@data-xpath='processDefinitionMenuOption']//a"
                + "[contains(text(), '%s')]", text)));
    }

    public WebElement myServices(String text) {
        return driver.findElement(By.xpath(String.format("//div[@data-xpath='processActiveMenuOption']//a[contains"
                + "(text(), '%s')]", text)));
    }

    public WebElement myTasks(String text) {
        return driver.findElement(By.xpath(String.format("//div[@data-xpath='taskMenuOption']//a[contains(text(), "
                + "'%s')]", text)));
    }

    public WebElement reports(String text) {
        return driver.findElement(By.xpath(String.format("//div[@data-xpath='reportMenuOption']//a[contains(text(), "
                + "'%s')]", text)));
    }

    public DashboardPage checkServices() {
        wait.until(visibilityOf(availableServices(availableServices)));
        wait.until(visibilityOf(myServices(myServices)));
        wait.until(visibilityOf(myTasks(myTasks)));
        wait.until(visibilityOf(reports(reports)));
        return this;
    }

    public AvailableServicesPage clickOnAvailableServices() {
        wait
                .until(visibilityOf(availableServices(availableServices)))
                .click();
        return new AvailableServicesPage();
    }
}
