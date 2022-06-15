package platform.qa.officer.pages;

import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;

import lombok.Getter;
import platform.qa.officer.panel.OfficerHeaderPanel;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyServicesPage extends OfficerBasePage {

    @Getter
    private final OfficerHeaderPanel headerPanel = new OfficerHeaderPanel();

    private final String myServicesTextUa = "Мої послуги";
    @FindBy(xpath = "//div[@data-xpath='header']/following-sibling::div//h1")
    private WebElement myServicesHeader;

    public MyServicesPage() {
        loadingPage();
        loadingComponents();
        checkMyServicesHeader();
    }

    public MyServicesPage checkMyServicesHeader() {
        wait.until(textToBePresentInElement(myServicesHeader, myServicesTextUa));
        return this;
    }
}
