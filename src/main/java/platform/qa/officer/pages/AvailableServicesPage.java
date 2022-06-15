package platform.qa.officer.pages;

import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import lombok.Getter;
import platform.qa.officer.panel.OfficerHeaderPanel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AvailableServicesPage extends OfficerBasePage {

    @Getter
    private final OfficerHeaderPanel headerPanel = new OfficerHeaderPanel();

    private final String availableServices = "Доступні послуги";
    @FindBy(xpath = "//div[@data-xpath='header']/following-sibling" + "::div//h1")
    private WebElement availableServicesHeader;

    public AvailableServicesPage() {
        loadingPage();
        loadingComponents();
        checkAvailableServicesHeader();
    }

    WebElement getProcessPath(String text) {
        return driver.findElement(By.xpath(String.format("//button[text()=\"%s\"]", text)));
    }

    public AvailableServicesPage checkAvailableServicesHeader() {
        wait.until(textToBePresentInElement(availableServicesHeader, availableServices));
        return this;
    }

    public void clickOnProcessByName(String processName) {
        wait.until(visibilityOf(getProcessPath(processName)))
                .click();
    }

    public AvailableServicesPage checkProcessByName(String processName) {
        wait.until(visibilityOf(getProcessPath(processName)));
        return this;
    }
}
